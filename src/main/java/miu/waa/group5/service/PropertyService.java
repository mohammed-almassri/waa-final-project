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
    Page<PropertyDTO> findProperties(String city, String state, Double minPrice, Double maxPrice,
                                     Integer minBedroomCount, Integer maxBedroomCount, Integer minBathroomCount,
                                     Integer maxBathroomCount, List<HomeType> homeTypes,
                                     Boolean hasParking, Boolean hasPool, Boolean hasAC, Pageable pageable);

    public PropertyResponse createProperty(PropertyRequest propertyRequest);
    public PropertyResponse updateProperty(PropertyRequest propertyRequest, long id);
    public void deleteProperty(long id);
    public Page<PropertyResponse> findByOwner(Pageable pageable);
    public PropertyResponse findById(long id);

    public void validateOwner(Property property);
    public Property convertToEntity(PropertyRequest propertyRequest);
    public void convertToEntity(PropertyRequest propertyRequest, Property destination);

    public PropertyResponse convertToDto(Property property);

    Page<PropertyResponse> findAll(Pageable pageable);
}
