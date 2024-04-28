package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.DetectiveImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetectiveImagesRepository extends JpaRepository<DetectiveImagesEntity, Long> {
}
