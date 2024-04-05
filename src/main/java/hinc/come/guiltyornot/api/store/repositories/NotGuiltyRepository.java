package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.GuiltyEntity;
import hinc.come.guiltyornot.api.store.entities.NotGuiltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface NotGuiltyRepository extends JpaRepository<NotGuiltyEntity, Long> {
    NotGuiltyEntity findByUserId(Long userId);
}
