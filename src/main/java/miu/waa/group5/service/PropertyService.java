package miu.waa.group5.service;

import miu.waa.group5.dto.PropertyRequest;
import miu.waa.group5.dto.PropertyResponse;
import miu.waa.group5.entity.Property;

import java.util.List;

public interface PropertyService {

    public PropertyResponse createProperty(PropertyRequest propertyRequest);
    public List<PropertyResponse> findByOwner();
    private Property convertToEntity(PropertyRequest propertyRequest) {
        return null;
    }

    private PropertyResponse convertToDto(PropertyResponse propertyResponse) {
        return null;
    }
}
