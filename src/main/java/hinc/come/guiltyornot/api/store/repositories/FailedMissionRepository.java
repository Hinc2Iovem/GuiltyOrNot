package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.FailedMissionEntity;
import org.springframework.data.repository.CrudRepository;

public interface FailedMissionRepository extends CrudRepository<FailedMissionEntity, Long> {
}
