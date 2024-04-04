package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.GuiltyEntity;
import hinc.come.guiltyornot.api.store.entities.NotGuiltyEntity;
import org.springframework.data.repository.CrudRepository;

public interface NotGuiltyRepository extends CrudRepository<NotGuiltyEntity, Long> {
    NotGuiltyEntity findByUserId(Long userId);
}
