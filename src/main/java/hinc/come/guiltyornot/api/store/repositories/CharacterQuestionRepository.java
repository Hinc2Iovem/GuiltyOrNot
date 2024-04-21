package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.CharacterQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface CharacterQuestionRepository extends JpaRepository<CharacterQuestionEntity, Long> {
    void deleteByIdAndCharacterId(Long questionId,Long characterId);
    Stream<CharacterQuestionEntity> findAllByCharacterId(Long characterId);
}
