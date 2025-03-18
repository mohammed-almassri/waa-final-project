package miu.waa.group5.service;

import miu.waa.group5.dto.PropertyDTO;
import miu.waa.group5.entity.HomeType;
import miu.waa.group5.dto.PropertyRequest;
import miu.waa.group5.dto.PropertyResponse;
import miu.waa.group5.entity.Property;

import java.util.List;


public interface PropertyService {
    List<PropertyDTO> findProperties(String city, String state, Double minPrice, Double maxPrice,
                                     Integer minBedroomCount, Integer maxBedroomCount, Integer minBathroomCount,
                                     Integer maxBathroomCount, List<HomeType> homeTypes,
                                     Boolean hasParking, Boolean hasPool, Boolean hasAC);

    public PropertyResponse createProperty(PropertyRequest propertyRequest);
    public List<PropertyResponse> findByOwner();
    private Property convertToEntity(PropertyRequest propertyRequest) {
        return null;
    }

    private PropertyResponse convertToDto(PropertyResponse propertyResponse) {
        return null;
    }
}
