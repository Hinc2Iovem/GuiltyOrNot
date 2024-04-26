package hinc.come.guiltyornot.api.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "character_victim")
public class CharacterVictimEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "mission_detective_id", referencedColumnName = "id")
    MissionDetectiveEntity missionDetective;

    @Column(name = "mission_detective_id", updatable = false, insertable = false)
    Long missionDetectiveId;

    @OneToOne
    @JoinColumn(name = "character_entity_id", referencedColumnName = "id")
    CharacterEntity character;

    @Column(name = "character_entity_id", updatable = false, insertable = false)
    Long characterEntityId;
}
