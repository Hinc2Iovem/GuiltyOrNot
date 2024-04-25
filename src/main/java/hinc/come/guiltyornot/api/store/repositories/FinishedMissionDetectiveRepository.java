package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.FinishedMissionDetectiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinishedMissionDetectiveRepository extends JpaRepository<FinishedMissionDetectiveEntity, Long> {
    FinishedMissionDetectiveEntity findByMissionIdAndDetectiveId(Long missionId, Long userId);

    List<FinishedMissionDetectiveEntity> findAllByDetectiveId(Long userId);
}
