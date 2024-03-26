package hinc.come.guiltyornot.store.repositories;

import hinc.come.guiltyornot.store.entities.AnswerEntity;
import hinc.come.guiltyornot.store.entities.QuestionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {
    Stream<QuestionEntity> streamAllBy();

    QuestionEntity findByTitle(String title);
}
