package miu.waa.group5.service;

import miu.waa.group5.dto.PropertyDTO;
import miu.waa.group5.entity.HomeType;
import miu.waa.group5.dto.PropertyRequest;
import miu.waa.group5.dto.PropertyResponse;
import miu.waa.group5.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PropertyService {
    List<PropertyDTO> findProperties(String city, String state, Double minPrice, Double maxPrice,
                                     Integer minBedroomCount, Integer maxBedroomCount, Integer minBathroomCount,
                                     Integer maxBathroomCount, List<HomeType> homeTypes,
                                     Boolean hasParking, Boolean hasPool, Boolean hasAC);

    public PropertyResponse createProperty(PropertyRequest propertyRequest);
    public List<PropertyResponse> findByOwner();

    public Property convertToEntity(PropertyRequest propertyRequest);

    public PropertyResponse convertToDto(Property property);

    Page<PropertyResponse> findAll(Pageable pageable);
}
