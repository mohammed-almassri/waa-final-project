package miu.waa.group5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miu.waa.group5.dto.AuthRequest;
import miu.waa.group5.dto.AuthResponse;
import miu.waa.group5.dto.UserRequest;
import miu.waa.group5.dto.UserResponse;
import miu.waa.group5.service.UserService;
import miu.waa.group5.util.JWTUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserResponse user = userService.findByName(request.getEmail());
            String jwt = jwtUtil.generateToken(request.getEmail());
        return ResponseEntity.ok(new AuthResponse(jwt, user.getEmail(),user.getName(), user.getImageUrl()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserResponse user = userService.findByName(username);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
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
}