package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.CharacterQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterQuestionRepository extends JpaRepository<CharacterQuestionEntity, Long> {
}
