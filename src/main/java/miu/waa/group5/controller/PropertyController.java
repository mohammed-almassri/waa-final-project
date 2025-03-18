package miu.waa.group5.controller;

import jakarta.validation.constraints.Min;
import miu.waa.group5.dto.PropertyDTO;
import miu.waa.group5.entity.HomeType;
import miu.waa.group5.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/customers/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getProperties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) @Min(value = 0, message = "minPrice can not be negative") Double minPrice,
            @RequestParam(required = false) @Min(value = 0, message = "maxPrice can not be negative") Double maxPrice,
            @RequestParam(required = false) @Min(value = 0, message = "minBedroomCount can not be negative") Integer minBedroomCount,
            @RequestParam(required = false) @Min(value = 0, message = "maxBedroomCount can not be negative") Integer maxBedroomCount,
            @RequestParam(required = false) @Min(value = 0, message = "minBathroomCount can not be negative") Integer minBathroomCount,
            @RequestParam(required = false) @Min(value = 0, message = "maxBathroomCount can not be negative") Integer maxBathroomCount,
            @RequestParam(required = false) String homeType,  // comma-separated list
            @RequestParam(required = false) Boolean hasParking,
            @RequestParam(required = false) Boolean hasPool,
            @RequestParam(required = false) Boolean hasAC
    ) {
        List<HomeType> homeTypes = null;
        if (homeType != null && !homeType.isEmpty()) {
            homeTypes = Arrays.stream(homeType.split(","))
                    .map(HomeType::valueOf)
                    .toList();
        }

        List<PropertyDTO> properties = propertyService.findProperties(
                city, state, minPrice, maxPrice, minBedroomCount, maxBedroomCount,
                minBathroomCount, maxBathroomCount, homeTypes, hasParking, hasPool, hasAC
        );
        return ResponseEntity.ok(properties);
    }
}