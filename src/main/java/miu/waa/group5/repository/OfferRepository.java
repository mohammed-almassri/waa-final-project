package miu.waa.group5.repository;

import miu.waa.group5.entity.Media;
import miu.waa.group5.entity.Offer;
import miu.waa.group5.entity.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {


    List<Offer> findByProperty_Owner_Name(@Param("username") String username);

    @Query("SELECT o FROM Offer o JOIN Property p WHERE p.id = :property_id AND o.isAccepted = null")
    List<Offer> findPendingOffersByProperty_Id(@Param("property_id") Long property_id);

    List<Offer> findAllByIdNotAndProperty_Id(@Param("id") Long id, @Param("property_id") Long property_id);

    List<Offer> findByCustomerId(Long customerId);
}
