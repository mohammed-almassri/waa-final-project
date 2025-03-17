package miu.waa.group5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


enum HomeType {
    HOUSE, TOWN_HOME, CONDO, APARTMENT;

    public String getReadableName() {
        List<String> subnames = Arrays.asList(this.name().split("_"));
        return subnames.stream().map((subname) -> subname.substring(0, 1).toUpperCase() + subname.substring(1).toLowerCase()).collect(Collectors.joining(" "));
    }
}
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String city;
    private String state;
    private String zipCode;
    private String address;

    private Double locationLat; // Geolocation latitude
    private Double locationLng; // Geolocation longitude

    private Double price;
    private Integer bedroomCount;
    private Integer bathroomCount;

    @Enumerated(EnumType.ORDINAL)
    private HomeType homeType; // House, Townhome, Condo, Apartment

    private Integer squareFootage;

    private Boolean hasParking;
    private Boolean hasPool;
    private Boolean hasAC;

    private Boolean isApproved;

    private LocalDateTime processedAt;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany
    @JoinColumn(name = "property_id", nullable = true)
    private List<Media> medias;
}
