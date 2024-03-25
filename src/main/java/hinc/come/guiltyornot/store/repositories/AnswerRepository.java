package hinc.come.guiltyornot.store.repositories;

import hinc.come.guiltyornot.store.entities.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<AnswerEntity, Long> {

}
