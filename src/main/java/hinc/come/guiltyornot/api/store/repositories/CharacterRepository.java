package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
    @Query(

    )
    Stream<CharacterEntity> findAllByUserIdAndRole(Long userId, String role);
}
