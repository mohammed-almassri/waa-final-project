package miu.waa.group5.repository;

import miu.waa.group5.entity.HomeType;
import miu.waa.group5.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository  extends JpaRepository<Property,Long> {

    @Query("SELECT p FROM Property p JOIN User u WHERE u.email = :username")
    List<Property> findByOwner_Email(@Param("username") String username);

}
