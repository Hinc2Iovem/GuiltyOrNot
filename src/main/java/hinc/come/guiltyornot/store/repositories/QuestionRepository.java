package hinc.come.guiltyornot.store.repositories;

import hinc.come.guiltyornot.store.entities.AnswerEntity;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<AnswerEntity, Long> {

}
