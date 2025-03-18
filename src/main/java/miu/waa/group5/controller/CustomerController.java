package miu.waa.group5.controller;


import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import miu.waa.group5.dto.*;
import miu.waa.group5.entity.HomeType;
import miu.waa.group5.entity.Offer;
import miu.waa.group5.service.FavoritesService;
import miu.waa.group5.service.OfferService;
import miu.waa.group5.service.PropertyService;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miu.waa.group5.service.UserService;
import miu.waa.group5.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final OfferService offerService;    
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PropertyService propertyService;
    private final FavoritesService favoritesService;
  
    @PostMapping("/offers")
    public ResponseEntity<OfferResponse> createOffer(@RequestBody OfferRequest offerRequest) {
        OfferResponse offerResponse = offerService.createOffer(offerRequest);
        return ResponseEntity.ok(offerResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>  createUser(@RequestBody @Valid SignupRequest userRequest) {
        var user = userService.registerUser(userRequest,"CUSTOMER");
        String jwt = jwtUtil.generateToken(userRequest.getEmail());
        return ResponseEntity.ok(new AuthResponse(jwt, user.getEmail(),user.getName(), user.getImageUrl()));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserResponse user = userService.findByName(request.getEmail());
            String jwt = jwtUtil.generateToken(request.getEmail());
            return ResponseEntity.ok(new AuthResponse(jwt, user.getEmail(),user.getName(), user.getImageUrl()));
    }

    @GetMapping("/properties")
    public ResponseEntity<List<PropertyDTO>> getProperties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) @Min(value = 0, message = "minPrice can not be negative") Double minPrice,
            @RequestParam(required = false) @Min(value = 0, message = "maxPrice can not be negative") Double maxPrice,
            @RequestParam(required = false) @Min(value = 0, message = "minBedroomCount can not be negative") Integer minBedroomCount,
            @RequestParam(required = false) @Min(value = 0, message = "maxBedroomCount can not be negative") Integer maxBedroomCount,
            @RequestParam(required = false) @Min(value = 0, message = "minBathroomCount can not be negative") Integer minBathroomCount,
            @RequestParam(required = false) @Min(value = 0, message = "maxBathroomCount can not be negative") Integer maxBathroomCount,
            @RequestParam(required = false) String homeType,  // comma-separated list
            @RequestParam(required = false) Boolean hasParking,
            @RequestParam(required = false) Boolean hasPool,
            @RequestParam(required = false) Boolean hasAC
    ) {
        List<HomeType> homeTypes = null;
        if (homeType != null && !homeType.isEmpty()) {
            homeTypes = Arrays.stream(homeType.split(","))
                    .map(HomeType::valueOf)
                    .toList();
        }

        List<PropertyDTO> properties = propertyService.findProperties(
                city, state, minPrice, maxPrice, minBedroomCount, maxBedroomCount,
                minBathroomCount, maxBathroomCount, homeTypes, hasParking, hasPool, hasAC
        );
        return ResponseEntity.ok(properties);
    }

    @PostMapping("/favorites")
    public ResponseEntity<FavoriteResponse> addFavorite( @Valid @RequestBody FavoriteRequest request) {
        FavoriteResponse response = favoritesService.addFavorite(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<PropertyResponse>> getCustomerFavorites() {
        List<PropertyResponse> favorites = favoritesService.getCustomerFavorites();
        return ResponseEntity.ok(favorites);
    }

    @GetMapping("/offers")
    public ResponseEntity<List<OfferListResponse>> getAllOffers() {
        List<OfferListResponse> offers = offerService.getAllOffers();
        return ResponseEntity.ok(offers);
    }
}
