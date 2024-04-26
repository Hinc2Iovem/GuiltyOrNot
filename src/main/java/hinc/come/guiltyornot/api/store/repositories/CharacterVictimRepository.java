package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.CharacterVictimEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CharacterVictimRepository extends JpaRepository<CharacterVictimEntity, Long> {
}
