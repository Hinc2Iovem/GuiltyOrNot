package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.CharacterGuiltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CharacterGuiltyRepository extends JpaRepository<CharacterGuiltyEntity, Long> {
}
