package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.CharacterAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Stream;

public interface CharacterAnswerRepository extends JpaRepository<CharacterAnswerEntity, Long> {
    List<CharacterAnswerEntity> findAllByCharacterQuestionId(Long questionId);

    void deleteByIdAndCharacterQuestionId(Long answerId, Long questionId);
}
