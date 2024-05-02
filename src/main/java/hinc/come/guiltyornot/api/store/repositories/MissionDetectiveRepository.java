package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.MissionDetectiveCharactersEntity;
import hinc.come.guiltyornot.api.store.entities.MissionDetectiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionDetectiveRepository extends JpaRepository<MissionDetectiveEntity, Long> {
    List<MissionDetectiveEntity> findAllBy();
    MissionDetectiveEntity findByTitle(String title);
    List<MissionDetectiveEntity> findAllByDetectiveId(Long detectiveId);
    List<MissionDetectiveEntity> findAllByRole(String role);

}
