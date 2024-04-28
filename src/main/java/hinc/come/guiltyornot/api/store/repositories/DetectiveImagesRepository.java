package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.DetectiveImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetectiveImagesRepository extends JpaRepository<DetectiveImagesEntity, Long> {
    List<DetectiveImagesEntity> findByDetectiveId(Long detectiveId);
}
