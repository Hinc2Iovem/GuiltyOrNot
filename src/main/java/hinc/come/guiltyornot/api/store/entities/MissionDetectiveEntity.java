package hinc.come.guiltyornot.api.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mission_detective")
public class MissionDetectiveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "mission_id", referencedColumnName = "id")
    MissionEntity mission;

    @Column(name = "mission_id", updatable = false, insertable = false)
    Long missionId;

    @OneToMany
    @JoinColumn(name = "characters_id")
    private List<CharacterEntity> characters;
}
