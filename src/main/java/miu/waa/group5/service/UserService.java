package miu.waa.group5.service;


import miu.waa.group5.dto.SignupRequest;
import miu.waa.group5.dto.UserRequest;
import miu.waa.group5.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse registerUser(SignupRequest request, String role);
    UserResponse findByName(String name);
    UserResponse updateUser(Long id, UserRequest userRequest);
    Page<UserResponse> findByRole(String role, Pageable pageable);
    public void toggleUserActivation(Long id);
    public void approveUser(Long id);
    UserResponse findById(Long id);
}
