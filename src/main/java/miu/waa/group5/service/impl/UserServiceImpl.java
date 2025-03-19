package miu.waa.group5.service.impl;

import miu.waa.group5.dto.SignupRequest;
import miu.waa.group5.dto.UserRequest;
import miu.waa.group5.dto.UserResponse;
import miu.waa.group5.entity.User;
import miu.waa.group5.repository.UserRepository;
import miu.waa.group5.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    @Autowired
    UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    //TODO: role should be an enum
    public UserResponse registerUser(SignupRequest request, String role) {
        var u = new User();
        u.setEnabled(true);
        u.setRole(role);
        System.out.println(request.getPassword());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setEmail(request.getEmail());
        u.setImageUrl(request.getImageURL());
        u.setName(request.getName());
        if(role.equals("OWNER")) {
            u.setApproved(false);
        }
        return modelMapper.map(userRepo.save(u), UserResponse.class);
    }

    public UserResponse findByName(String username) {
        var u = userRepo.findByEmail(username);
        return u.map(user -> modelMapper.map(user, UserResponse.class)).orElseThrow(() -> new RuntimeException("no user with the username" + username));
    }



    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        // Fetch the user by ID from repository
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setEmail(userRequest.getEmail());
        existingUser.setName(userRequest.getName());
        existingUser.setImageUrl(userRequest.getImageUrl());

        User savedUser = userRepo.save(existingUser);

        // Convert the saved user entity to UserResponse and return it
        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public Page<UserResponse> findByRole(String role, Pageable pageable) {
        var users = userRepo.findByRole(role,pageable);
        return users.map(user -> modelMapper.map(user, UserResponse.class));
    }

    @Override
    public void toggleUserActivation(Long id) {
        Optional<User> userOptional = userRepo.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(!user.isActive());
            userRepo.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void approveUser(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setApproved(true);
            userRepo.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public UserResponse findById(Long id) {
        return userRepo.findById(id).map(user -> modelMapper.map(user, UserResponse.class)).orElse(null);
    }
}
