package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.MissionGuiltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionGuiltyRepository extends JpaRepository<MissionGuiltyEntity, Long> {
    List<MissionGuiltyEntity> findAllByGuiltyId(Long guiltyId);
}
