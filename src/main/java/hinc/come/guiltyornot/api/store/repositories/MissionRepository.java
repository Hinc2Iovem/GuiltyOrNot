package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.MissionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface MissionRepository extends CrudRepository<MissionEntity, Long> {
    Stream<MissionEntity> streamAllBy();

    MissionEntity findByTitle(String title);
}
