package miu.waa.group5.service.impl;

import miu.waa.group5.dto.PropertyDTO;
import miu.waa.group5.entity.HomeType;
import miu.waa.group5.entity.Property;
import miu.waa.group5.repository.PropertyRepository;
import miu.waa.group5.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public List<PropertyDTO> findProperties(String city, String state, Double minPrice, Double maxPrice,
                                            Integer minBedroomCount, Integer maxBedroomCount, Integer minBathroomCount,
                                            Integer maxBathroomCount, List<HomeType> homeTypes,
                                            Boolean hasParking, Boolean hasPool, Boolean hasAC) {

        List<Property> properties = propertyRepository.filterProperties(
                city, state, minPrice, maxPrice, minBedroomCount, maxBedroomCount,
                minBathroomCount, maxBathroomCount, hasParking, hasPool, hasAC, homeTypes
        );

        return properties.stream()
                .map(PropertyDTO::fromEntity)
                .toList();
    }
}
