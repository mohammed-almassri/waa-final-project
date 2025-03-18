package miu.waa.group5.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyResponse {
    private Long id;
    private String title;
    private String description;
    private String city;
    private String state;
    private String zipCode;
    private String address;
    private Double locationLat;
    private Double locationLng;
    private int price;
    private int bedroomCount;
    private int bathroomCount;
    private String homeType;
    private int squareFootage;
    private boolean hasParking;
    private boolean hasPool;
    private boolean hasAC;
    private boolean isApproved;
    private LocalDateTime processedAt;
    private Long ownerId;
    private String status;

    private List<String> imageURLs;
}