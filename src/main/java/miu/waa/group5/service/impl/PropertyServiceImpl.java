package miu.waa.group5.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import miu.waa.group5.dto.PropertyDTO;
import miu.waa.group5.entity.HomeType;
import miu.waa.group5.entity.Property;
import miu.waa.group5.repository.PropertyRepository;
import miu.waa.group5.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PropertyDTO> findProperties(String city, String state, Double minPrice, Double maxPrice,
                                            Integer minBedroomCount, Integer maxBedroomCount, Integer minBathroomCount,
                                            Integer maxBathroomCount, List<HomeType> homeTypes,
                                            Boolean hasParking, Boolean hasPool, Boolean hasAC) {

        // 1. grab CriteriaBuilder
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> cq = cb.createQuery(Property.class);
        Root<Property> property = cq.from(Property.class);

        List<Predicate> predicates = new ArrayList<>();

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
        List<Property> properties = entityManager.createQuery(cq).getResultList();

        return properties.stream()
                .map(PropertyDTO::fromEntity)
                .toList();
    }
}
