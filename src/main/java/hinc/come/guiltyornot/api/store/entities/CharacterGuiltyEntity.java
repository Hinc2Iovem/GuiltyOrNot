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
@Table(name = "character_guilty")
public class CharacterGuiltyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "character_entity_id", referencedColumnName = "id")
    CharacterEntity character;

    @Column(name = "character_entity_id", updatable = false, insertable = false)
    Long characterEntityId;

    @ManyToOne
    @JoinColumn(name = "mission_detective_id", referencedColumnName = "id")
    MissionDetectiveEntity mission;

    @Column(name = "mission_detective_id", updatable = false, insertable = false)
    Long missionId;
}
