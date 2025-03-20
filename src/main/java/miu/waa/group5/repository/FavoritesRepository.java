package miu.waa.group5.repository;

import miu.waa.group5.entity.Favorites;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
    Page<Favorites> findByCustomerId(Long customerId, Pageable pageable);

    boolean existsByCustomerIdAndPropertyId(Long customerId, Long propertyId);

    Optional<Favorites> findByCustomerIdAndPropertyId(Long customerId, Long propertyId);

    Optional<Favorites> findByIdAndCustomerId(Long id, Long customerId);
}
