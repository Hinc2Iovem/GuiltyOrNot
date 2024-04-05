package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.FinishedMissionGuiltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinishedMissionGuiltyRepository extends JpaRepository<FinishedMissionGuiltyEntity, Long> {
    FinishedMissionGuiltyEntity findByMissionIdAndUserId(Long missionId, Long userId);
}
