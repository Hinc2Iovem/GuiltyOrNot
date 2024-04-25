package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.CharacterQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterQuestionRepository extends JpaRepository<CharacterQuestionEntity, Long> {
    void deleteByIdAndCharacterEntityId(Long questionId,Long characterId);
    List<CharacterQuestionEntity> findAllByCharacterEntityId(Long characterId);
}
