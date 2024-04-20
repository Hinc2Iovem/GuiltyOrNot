package hinc.come.guiltyornot.api.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "character")
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String description;

    String name;

    String hair_color;

    Integer age;

    String feature;

    @Builder.Default
    Boolean isGuilty = false;

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "character_id", referencedColumnName = "id")
    List<CharacterQuestionEntity> characterQuestions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "mission_id", referencedColumnName = "id")
    MissionEntity mission;

    @Column(name = "mission_id", updatable = false, insertable = false)
    Long missionId;
}
