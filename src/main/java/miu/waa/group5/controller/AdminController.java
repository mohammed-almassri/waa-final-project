package miu.waa.group5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miu.waa.group5.dto.*;
import miu.waa.group5.dto.base.BaseResponse;
import miu.waa.group5.service.PropertyService;
import miu.waa.group5.service.UserService;
import miu.waa.group5.util.JWTUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final PropertyService propertyService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
//          try{
              authenticationManager.authenticate(
                      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
              );
              UserResponse user = userService.findByName(request.getEmail());
              String jwt = jwtUtil.generateToken(request.getEmail());
              return ResponseEntity.ok(new AuthResponse(jwt, user.getEmail(),user.getName(), user.getImageUrl()));
//          }
//          catch (Exception e) {
//              System.out.println("Exception: " + e.getClass());
//          }
//          return null;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserResponse user = userService.findByName(username);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/me")
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserRequest userRequest) {
        try {
            // Fetch the authenticated user's id (if you are using Spring Security)
            String username =  SecurityContextHolder.getContext().getAuthentication().getName();
            var user = userService.findByName(username);
            // Update the user details via the service layer
            UserResponse updatedUser = userService.updateUser(user.getId(), userRequest);

            // Return the updated user's details
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            // Handle any exceptions (e.g., user not found or invalid data)
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/owners")
    public Page<UserResponse> getOwners(
            @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> ownersPage = userService.findByRole("OWNER", pageable);
        return ownersPage;
    }

    @GetMapping("/properties")
    public Page<PropertyResponse> getProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PropertyResponse> pPage = propertyService.findAll(pageable);
        return pPage;
    }

    @PatchMapping("/owners/{id}/approve")
    public BaseResponse<String> approveOwner(@PathVariable long id) {
        userService.approveUser(id);
        return new BaseResponse<>("success","Approved");
    }

    @PatchMapping("/owners/{id}/activate")
    public BaseResponse<String> activateOwner(@PathVariable long id) {
        userService.toggleUserActivation(id);
        return new BaseResponse<>("success","Activated");
    }
}