package miu.waa.group5.repository;

import miu.waa.group5.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media,Long> {

    Media findMediaByUrl(String url);

    List<Media> findAllByUrlIn(Collection<String> urls);
}
