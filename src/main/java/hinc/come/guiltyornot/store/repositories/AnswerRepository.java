package hinc.come.guiltyornot.store.repositories;

import hinc.come.guiltyornot.store.entities.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

}
