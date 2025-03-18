package miu.waa.group5.repository;

import miu.waa.group5.entity.Media;
import miu.waa.group5.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {

}
