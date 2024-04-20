package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.CharacterAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterAnswerRepository extends JpaRepository<CharacterAnswerEntity, Long> {
}
