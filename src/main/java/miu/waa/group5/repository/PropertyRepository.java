package miu.waa.group5.repository;

import miu.waa.group5.entity.HomeType;
import miu.waa.group5.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository  extends JpaRepository<Property,Long> {
    @Query("SELECT p FROM Property p WHERE " +
            "(:city IS NULL OR p.city = :city) AND " +
            "(:state IS NULL OR p.state = :state) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:minBedroomCount IS NULL OR p.bedroomCount >= :minBedroomCount) AND " +
            "(:maxBedroomCount IS NULL OR p.bedroomCount <= :maxBedroomCount) AND " +
            "(:minBathroomCount IS NULL OR p.bathroomCount >= :minBathroomCount) AND " +
            "(:maxBathroomCount IS NULL OR p.bathroomCount <= :maxBathroomCount) AND " +
            "(:hasParking IS NULL OR p.hasParking = :hasParking) AND " +
            "(:hasPool IS NULL OR p.hasPool = :hasPool) AND " +
            "(:hasAC IS NULL OR p.hasAC = :hasAC) AND " +
            "(:homeTypes IS NULL OR p.homeType IN :homeTypes)")
    List<Property> filterProperties(
            @Param("city") String city,
            @Param("state") String state,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minBedroomCount") Integer minBedroomCount,
            @Param("maxBedroomCount") Integer maxBedroomCount,
            @Param("minBathroomCount") Integer minBathroomCount,
            @Param("maxBathroomCount") Integer maxBathroomCount,
            @Param("hasParking") Boolean hasParking,
            @Param("hasPool") Boolean hasPool,
            @Param("hasAC") Boolean hasAC,
            @Param("homeTypes") List<HomeType> homeTypes
    );
}
