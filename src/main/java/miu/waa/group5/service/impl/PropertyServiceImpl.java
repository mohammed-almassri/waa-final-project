package miu.waa.group5.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import miu.waa.group5.dto.PropertyDTO;
import miu.waa.group5.dto.PropertyRequest;
import miu.waa.group5.dto.PropertyResponse;
import miu.waa.group5.entity.*;
import miu.waa.group5.repository.MediaRepository;
import miu.waa.group5.repository.PropertyRepository;
import miu.waa.group5.repository.UserRepository;
import miu.waa.group5.service.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public Page<PropertyDTO> findProperties(String city, String state, Double minPrice, Double maxPrice,
                                            Integer minBedroomCount, Integer maxBedroomCount, Integer minBathroomCount,
                                            Integer maxBathroomCount, List<HomeType> homeTypes,
                                            Boolean hasParking, Boolean hasPool, Boolean hasAC, Pageable pageable) {

        List<Predicate> predicates = new ArrayList<>();

        // 1. grab CriteriaBuilder
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> cq = cb.createQuery(Property.class);
        Root<Property> property = cq.from(Property.class);

        // 2. Add query conditions dynamically based on the parameters passed in
        if (city != null) {
                predicates.add(cb.equal(property.get("city"), city));
        }
        if (state != null) {
                predicates.add(cb.equal(property.get("state"), state));
        }
        if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(property.get("price"), minPrice));
        }
        if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(property.get("price"), maxPrice));
        }
        if (minBedroomCount != null) {
                predicates.add(cb.greaterThanOrEqualTo(property.get("bedroomCount"), minBedroomCount));
        }
        if (maxBedroomCount != null) {
                predicates.add(cb.lessThanOrEqualTo(property.get("bedroomCount"), maxBedroomCount));
        }
        if (minBathroomCount != null) {
                predicates.add(cb.greaterThanOrEqualTo(property.get("bathroomCount"), minBathroomCount));
        }
        if (maxBathroomCount != null) {
                predicates.add(cb.lessThanOrEqualTo(property.get("bathroomCount"), maxBathroomCount));
        }
        if (hasParking != null) {
                predicates.add(cb.equal(property.get("hasParking"), hasParking));
        }
        if (hasPool != null) {
                predicates.add(cb.equal(property.get("hasPool"), hasPool));
        }
        if (hasAC != null) {
                predicates.add(cb.equal(property.get("hasAC"), hasAC));
        }
        if (homeTypes != null && !homeTypes.isEmpty()) {
                predicates.add(property.get("homeType").in(homeTypes));
        }

        // 3. final query
        cq.where(predicates.toArray(new Predicate[0]));

        // 4. return result
        List<Property> properties = entityManager.createQuery(cq)
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        Page<Property> pageProperties = new PageImpl<Property>(properties, pageable, properties.size());

        return pageProperties
                .map(PropertyDTO::fromEntity);

    }

    @Transactional
    public PropertyResponse createProperty(PropertyRequest propertyRequest) {
        Property property = convertToEntity(propertyRequest);
        List<Media> medias = property.getMedias() != null ? property.getMedias() : new ArrayList<>();
        //TODO: query in loop
        propertyRequest.getImageURLs().forEach((url) -> {
            Media media = mediaRepository.findMediaByUrl(url);
            if (media != null) {
                medias.add(media);
            }
        });
        property.setMedias(medias);
        property.setStatus(StatusType.AVAILABLE);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("no user with the username" + username));
        property.setOwner(user);
        propertyRepository.save(property);
        return convertToDto(property);

    }

    @Transactional
    public PropertyResponse updateProperty(PropertyRequest propertyRequest, long id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new RuntimeException("no property with id: " + id));
        convertToEntity(propertyRequest, property);
        validateOwner(property);
        List<Media> medias = mediaRepository.findAllByUrlIn(propertyRequest.getImageURLs());
        property.setMedias(medias);
        propertyRepository.save(property);
        return convertToDto(property);
    }

    @Transactional
    public void deleteProperty(long id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new RuntimeException("no property with id: " + id));
        validateOwner(property);
        if (property.getStatus() == StatusType.AVAILABLE) {
            propertyRepository.delete(property);
        } else {
            throw new RuntimeException("Property status is invalid for deletion");
        }

    }




    public Page<PropertyResponse> findByOwner(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Page<Property> properties = propertyRepository.findByOwner_Email(username, pageable);
        return properties.map(this::convertToDto);
    }

    public PropertyResponse findById(long id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new RuntimeException("no property with id: " + id));
        return convertToDto(property);
    }

    public void convertToEntity(PropertyRequest propertyRequest, Property destination) {
        modelMapper.map(propertyRequest, destination);
        Optional<HomeType> homeType = HomeType.getEnumByString(propertyRequest.getHomeType());
        homeType.ifPresent(destination::setHomeType);
    }

    public void validateOwner(Property property) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("no user with the username" + username));

        if (!user.getEmail().equals(property.getOwner().getEmail())) {
            throw new RuntimeException("you don't own this property");
        }
    }


    public Property convertToEntity(PropertyRequest propertyRequest) {
        Property property = modelMapper.map(propertyRequest, Property.class);
        Optional<HomeType> homeType = HomeType.getEnumByString(propertyRequest.getHomeType());
        homeType.ifPresent(property::setHomeType);
        return property;
    }

    @Override
    public Page<PropertyResponse> findAll(Pageable pageable) {
        var properties = propertyRepository.findAll(pageable);
        return properties.map(p->modelMapper.map(p,PropertyResponse.class));
    }

    @Override
    public PropertyResponse convertToDto(Property property) {
        PropertyResponse propertyResponse = modelMapper.map(property, PropertyResponse.class);
        String homeType = property.getHomeType() != null ? property.getHomeType().getReadableName() : null;
        propertyResponse.setHomeType(homeType);
        propertyResponse.setStatus(property.getStatus().getReadableName());
        List<String> urls = property.getMedias().stream().map(Media::getUrl).toList();
        propertyResponse.setImageURLs(urls);
        propertyResponse.setOwnerId(property.getOwner().getId());
        return propertyResponse;
    }

}
