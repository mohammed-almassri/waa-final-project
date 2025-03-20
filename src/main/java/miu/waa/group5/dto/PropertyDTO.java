package miu.waa.group5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.waa.group5.entity.HomeType;
import miu.waa.group5.entity.Media;
import miu.waa.group5.entity.Property;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {
    private Long id;
    private String title;
    private Double price;
    private String city;
    private String state;
    private List<String> imageURLs;
    private double squareFootage;
    private int bedroomCount;
    private int bathroomCount;
    private String description;
    private String address;
    private Boolean hasParking;
    private Boolean hasPool;
    private Boolean hasAC;
    private HomeType homeType;

    public static PropertyDTO fromEntity(Property property) {
        PropertyDTO dto = new PropertyDTO();
        dto.setId(property.getId());
        dto.setTitle(property.getTitle());
        dto.setPrice(property.getPrice());
        dto.setCity(property.getCity());
        dto.setState(property.getState());
        dto.setImageURLs(property.getMedias().stream().map(Media::getUrl).collect(Collectors.toList()));
        dto.setSquareFootage(property.getSquareFootage());
        dto.setBedroomCount(property.getBedroomCount());
        dto.setBathroomCount(property.getBathroomCount());
        dto.setDescription(property.getDescription());
        dto.setAddress(property.getAddress());
        dto.setHasParking(property.getHasParking());
        dto.setHasPool(property.getHasPool());
        dto.setHasAC(property.getHasAC());
        dto.setHomeType(property.getHomeType());

        return dto;
    }
}