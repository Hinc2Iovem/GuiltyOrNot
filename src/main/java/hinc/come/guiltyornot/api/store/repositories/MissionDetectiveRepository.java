package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.MissionDetectiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionDetectiveRepository extends JpaRepository<MissionDetectiveEntity, Long> {
}
