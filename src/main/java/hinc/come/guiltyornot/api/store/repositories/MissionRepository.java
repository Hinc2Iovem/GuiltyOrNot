package hinc.come.guiltyornot.api.store.repositories;

import hinc.come.guiltyornot.api.store.entities.MissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface MissionRepository extends JpaRepository<MissionEntity, Long> {
    List<MissionEntity> findAllBy();
    List<MissionEntity> findAllByRole(String role);
    MissionEntity findByTitle(String title);

    List<MissionEntity> findAllByUserId(Long userId);
}
