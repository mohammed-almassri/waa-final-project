package miu.waa.group5.service;


import miu.waa.group5.dto.SignupRequest;
import miu.waa.group5.dto.UserRequest;
import miu.waa.group5.dto.UserResponse;

public interface UserService {
    UserResponse registerUser(SignupRequest request, String role);
    UserResponse findByName(String name);
    UserResponse updateUser(Long id, UserRequest userRequest);
}
