package miu.waa.group5.service.impl;

import miu.waa.group5.dto.PropertyRequest;
import miu.waa.group5.dto.PropertyResponse;
import miu.waa.group5.dto.UserResponse;
import miu.waa.group5.entity.HomeType;
import miu.waa.group5.entity.Media;
import miu.waa.group5.entity.Property;
import miu.waa.group5.entity.User;
import miu.waa.group5.repository.MediaRepository;
import miu.waa.group5.repository.PropertyRepository;
import miu.waa.group5.repository.UserRepository;
import miu.waa.group5.service.PropertyService;
import miu.waa.group5.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public PropertyResponse createProperty(PropertyRequest propertyRequest) {
        Property property = convertToEntity(propertyRequest);
        propertyRequest.getImageURLs().forEach((url) -> {
            Media media = mediaRepository.findMediaByUrl(url);
            if (media != null && !property.getMedias().contains(media)) {
                property.getMedias().add(media);
            }
        });
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("no user with the username" + username));
        property.setOwner(user);
        propertyRepository.save(property);
        return convertToDto(property);

    }

    public List<PropertyResponse> findByOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Property> properties = propertyRepository.findByOwner_Email(username);
        return properties.stream().map(this::convertToDto).toList();
    }


    private Property convertToEntity(PropertyRequest propertyRequest) {
        Property property = modelMapper.map(propertyRequest, Property.class);
        Optional<HomeType> homeType = HomeType.getEnumByString(propertyRequest.getHomeType());
        homeType.ifPresent(property::setHomeType);
        return property;
    }

    private PropertyResponse convertToDto(Property property) {
        PropertyResponse propertyResponse = modelMapper.map(property, PropertyResponse.class);
        propertyResponse.setHomeType(property.getHomeType().getReadableName());
        List<String> urls = property.getMedias().stream().map(Media::getUrl).toList();
        propertyResponse.setImageURLs(urls);
        propertyResponse.setOwnerId(property.getOwner().getId());
        return propertyResponse;
    }

}