package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.QuestionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {
    Stream<QuestionEntity> streamAllBy();

    QuestionEntity findByTitle(String title);
}
