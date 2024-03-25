package hinc.come.guiltyornot.store.repositories;

import hinc.come.guiltyornot.store.entities.FailedMissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<FailedMissionEntity, Long> {
}
