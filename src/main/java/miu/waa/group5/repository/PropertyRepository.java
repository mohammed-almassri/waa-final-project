package miu.waa.group5.repository;

import miu.waa.group5.entity.HomeType;
import miu.waa.group5.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository  extends JpaRepository<Property,Long> {


    Page<Property> findByOwner_Email(String ownerEmail, Pageable pageable);

    Page<Property> findAll(Pageable pageable);
}
