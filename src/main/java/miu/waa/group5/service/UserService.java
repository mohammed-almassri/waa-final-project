package miu.waa.group5.service;


import miu.waa.group5.dto.AuthRequest;
import miu.waa.group5.dto.UserRequest;
import miu.waa.group5.dto.UserResponse;

public interface UserService {
    UserResponse registerUser(AuthRequest authRequest);
    UserResponse findByName(String name);
    UserResponse updateUser(Long id, UserRequest userRequest);
}
