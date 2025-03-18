package miu.waa.group5.service;

import miu.waa.group5.dto.PropertyDTO;
import miu.waa.group5.entity.HomeType;
import miu.waa.group5.entity.Property;
import miu.waa.group5.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PropertyService {
    List<PropertyDTO> findProperties(String city, String state, Double minPrice, Double maxPrice,
                                     Integer minBedroomCount, Integer maxBedroomCount, Integer minBathroomCount,
                                     Integer maxBathroomCount, List<HomeType> homeTypes,
                                     Boolean hasParking, Boolean hasPool, Boolean hasAC);
}
