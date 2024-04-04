package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.DetectiveEntity;
import hinc.come.guiltyornot.api.store.entities.GuiltyEntity;
import org.springframework.data.repository.CrudRepository;

public interface GuiltyRepository extends CrudRepository<GuiltyEntity, Long> {
    GuiltyEntity findByUserId(Long userId);
}
