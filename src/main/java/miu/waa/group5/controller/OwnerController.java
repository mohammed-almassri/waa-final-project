package miu.waa.group5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miu.waa.group5.dto.AuthRequest;
import miu.waa.group5.dto.AuthResponse;
import miu.waa.group5.dto.SignupRequest;
import miu.waa.group5.dto.UserResponse;
import miu.waa.group5.service.UserService;
import miu.waa.group5.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse>  createUser(@RequestBody @Valid SignupRequest userRequest) {
        var user = userService.registerUser(userRequest,"OWNER");
        String jwt = jwtUtil.generateToken(userRequest.getEmail());
        return ResponseEntity.ok(new AuthResponse(jwt, user.getEmail(),user.getName(), user.getImageUrl()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserResponse user = userService.findByName(request.getEmail());
            String jwt = jwtUtil.generateToken(request.getEmail());
            return ResponseEntity.ok(new AuthResponse(jwt, user.getEmail(),user.getName(), user.getImageUrl()));
    }
}
