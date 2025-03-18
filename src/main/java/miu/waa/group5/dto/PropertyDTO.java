package miu.waa.group5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    public static PropertyDTO fromEntity(Property property) {
        PropertyDTO dto = new PropertyDTO();
        dto.setId(property.getId());
        dto.setTitle(property.getTitle());
        dto.setPrice(property.getPrice());
        dto.setCity(property.getCity());
        dto.setState(property.getState());
        dto.setImageURLs(property.getMedias().stream().map(Media::getUrl).collect(Collectors.toList()));
        return dto;
    }
}