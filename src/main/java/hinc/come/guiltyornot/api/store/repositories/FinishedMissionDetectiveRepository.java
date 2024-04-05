package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.FinishedMissionDetectiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinishedMissionDetectiveRepository extends JpaRepository<FinishedMissionDetectiveEntity, Long> {
    FinishedMissionDetectiveEntity findByMissionIdAndUserId(Long missionId, Long userId);
}
