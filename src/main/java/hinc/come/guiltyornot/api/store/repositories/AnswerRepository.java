package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Stream;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    Stream<AnswerEntity> streamAllBy();
    AnswerEntity findByQuestionId(Long questionId);
    AnswerEntity findByText(String text);
    List<AnswerEntity> findAllByQuestionId(Long questionId);
}
