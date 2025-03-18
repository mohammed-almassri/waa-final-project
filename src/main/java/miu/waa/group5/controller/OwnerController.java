package miu.waa.group5.controller;

import jakarta.validation.Valid;
import miu.waa.group5.dto.PropertyRequest;
import miu.waa.group5.dto.PropertyResponse;
import miu.waa.group5.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    @Autowired
    PropertyService propertyService;



    @PostMapping("properties")
    public ResponseEntity<PropertyResponse> createProperty(@RequestBody  @Valid PropertyRequest propertyRequest) {
        PropertyResponse propertyResponse = propertyService.createProperty(propertyRequest);
        return ResponseEntity.ok(propertyResponse);
    }

    @GetMapping("properties")
    public ResponseEntity<List<PropertyResponse>> getProperties() {
        List<PropertyResponse> propertyResponses = propertyService.findByOwner();
        return ResponseEntity.ok(propertyResponses);
    }


}
