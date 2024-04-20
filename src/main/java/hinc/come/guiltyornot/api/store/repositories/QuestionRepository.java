package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    Stream<QuestionEntity> streamAllBy();

    QuestionEntity findByTitle(String title);

    List<QuestionEntity> findAllByMissionGuiltyId(Long missionGuiltyId);
}
