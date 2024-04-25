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
@Table(name = "character_entity")
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String description;

    String name;

    String hairColor;

    String gender;

    Integer age;

    @Builder.Default
    Integer levelOfDifficulty = 1;

    String feature;

    @Builder.Default
    Boolean isGuilty = false;

    @ManyToOne
    @JoinColumn(name = "mission_detective_id", referencedColumnName = "id")
    MissionDetectiveEntity missionDetective;

    @Column(name = "mission_detective_id", updatable = false, insertable = false)
    Long missionDetectiveId;

    @ManyToOne
    @JoinColumn(name = "detective_id", referencedColumnName = "id")
    DetectiveEntity detective;

    @Column(name = "detective_id", updatable = false, insertable = false)
    Long detectiveId;
}
