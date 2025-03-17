package miu.waa.group5.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zip code is required")
    private String zipCode;

    @NotBlank(message = "Address is required")
    private String address;

    private Double locationLat;
    private Double locationLng;

    @Positive(message = "Price must be positive")
    private int price;

    @Min(value = 1, message = "At least 1 bedroom is required")
    private int bedroomCount;

    @Min(value = 1, message = "At least 1 bathroom is required")
    private int bathroomCount;

    @NotBlank(message = "Home type is required")
    private String homeType;

    @Positive(message = "Square footage must be positive")
    private int squareFootage;

    private boolean hasParking;
    private boolean hasPool;
    private boolean hasAC;
}