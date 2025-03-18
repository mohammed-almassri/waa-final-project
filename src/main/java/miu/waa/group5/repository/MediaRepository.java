package miu.waa.group5.repository;

import miu.waa.group5.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media,Long> {

    Media findMediaByUrl(String url);
}
