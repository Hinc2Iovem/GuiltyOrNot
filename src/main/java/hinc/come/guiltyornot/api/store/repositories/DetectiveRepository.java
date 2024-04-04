package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.DetectiveEntity;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface DetectiveRepository extends CrudRepository<DetectiveEntity, Long> {
    DetectiveEntity findByUserId(Long userId);
}
