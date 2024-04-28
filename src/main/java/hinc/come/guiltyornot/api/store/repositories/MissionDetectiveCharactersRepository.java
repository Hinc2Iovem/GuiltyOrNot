package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.MissionDetectiveCharactersEntity;
import hinc.come.guiltyornot.api.store.entities.MissionDetectiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionDetectiveCharactersRepository extends JpaRepository<MissionDetectiveCharactersEntity, Long> {
    List<MissionDetectiveCharactersEntity> findByMissionDetectiveId(Long missionId);
}
