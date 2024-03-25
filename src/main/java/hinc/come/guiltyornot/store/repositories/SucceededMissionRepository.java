package hinc.come.guiltyornot.store.repositories;

import hinc.come.guiltyornot.store.entities.FailedMissionEntity;
import hinc.come.guiltyornot.store.entities.SucceededMissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SucceededMissionRepository extends CrudRepository<SucceededMissionEntity, Long> {
}
