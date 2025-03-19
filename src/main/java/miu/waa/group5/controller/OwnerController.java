package miu.waa.group5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miu.waa.group5.dto.*;
import miu.waa.group5.service.OfferService;
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


import miu.waa.group5.service.PropertyService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/owners")
public class OwnerController {


    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PropertyService propertyService;
    private final OfferService offerService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>  createUser(@RequestBody @Valid SignupRequest userRequest) {
        var user = userService.registerUser(userRequest,"OWNER");
        String jwt = jwtUtil.generateToken(userRequest.getEmail());
        return ResponseEntity.ok(new AuthResponse(jwt, user.getId(), user.getEmail(),user.getName(), user.getImageUrl()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserResponse user = userService.findByName(request.getEmail());
            String jwt = jwtUtil.generateToken(request.getEmail());
            return ResponseEntity.ok(new AuthResponse(jwt, user.getId(), user.getEmail(),user.getName(), user.getImageUrl()));
    }



    @PostMapping("properties")
    public ResponseEntity<PropertyResponse> createProperty(@RequestBody  @Valid PropertyRequest propertyRequest) {
        PropertyResponse propertyResponse = propertyService.createProperty(propertyRequest);
        return ResponseEntity.ok(propertyResponse);
    }

    @GetMapping("properties")
    public ResponseEntity<List<PropertyResponse>> getProperties() {
        List<PropertyResponse> propertyResponses = propertyService.findByOwner();
        return ResponseEntity.ok(propertyResponses);
    }

    @GetMapping("offers")
    public ResponseEntity<List<OwnerOffersResponse>> getOffers() {
        List<OwnerOffersResponse> offersResponses = offerService.findByOwner();
        return ResponseEntity.ok(offersResponses);
    }
    @PatchMapping("offers/{id}")
    public ResponseEntity<OfferJudgeResponse> judgeOffer(@RequestBody OfferJudgeRequest offerJudgeRequest, @PathVariable("id") Long id) {
        OfferJudgeResponse offerJudgeResponse = offerService.judgeOffer(offerJudgeRequest, id);
        return ResponseEntity.ok(offerJudgeResponse);
    }

    @PatchMapping("offers/{id}/finalize")
    public ResponseEntity<OfferFinalizeResponse> finalizeOffer(@RequestBody OfferFinalizeRequest offerFinalizeRequest, @PathVariable("id") Long id) {
        OfferFinalizeResponse offerFinalizeResponse = offerService.finalizeOffer(offerFinalizeRequest, id);
        return ResponseEntity.ok(offerFinalizeResponse);

    }



}
