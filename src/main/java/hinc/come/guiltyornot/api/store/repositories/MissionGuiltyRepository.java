package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.MissionGuiltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionGuiltyRepository extends JpaRepository<MissionGuiltyEntity, Long> {
}
