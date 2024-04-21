package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
    Stream<CharacterEntity> findAllDetectiveId(Long detectiveId);
}
