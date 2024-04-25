package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.FinishedMissionDetectiveEntity;
import hinc.come.guiltyornot.api.store.entities.FinishedMissionGuiltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinishedMissionGuiltyRepository extends JpaRepository<FinishedMissionGuiltyEntity, Long> {
    FinishedMissionGuiltyEntity findByMissionIdAndGuiltyId(Long missionId, Long userId);

    List<FinishedMissionGuiltyEntity> findAllByGuiltyId(Long userId);
}
