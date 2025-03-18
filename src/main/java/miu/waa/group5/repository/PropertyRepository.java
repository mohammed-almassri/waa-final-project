package miu.waa.group5.repository;

import miu.waa.group5.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository  extends JpaRepository<Property,Long> {
}
