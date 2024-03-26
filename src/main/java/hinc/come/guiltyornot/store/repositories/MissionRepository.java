package hinc.come.guiltyornot.store.repositories;

import hinc.come.guiltyornot.store.entities.FailedMissionEntity;
import hinc.come.guiltyornot.store.entities.MissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface MissionRepository extends CrudRepository<MissionEntity, Long> {
    Stream<MissionEntity> streamAllBy();

    MissionEntity findByTitle(String title);
}
