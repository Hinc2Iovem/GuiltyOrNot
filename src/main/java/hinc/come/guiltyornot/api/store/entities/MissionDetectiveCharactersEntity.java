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
@Table(name = "mission_detective_characters")
public class MissionDetectiveCharactersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "mission_detective_id", referencedColumnName = "id")
    MissionDetectiveEntity missionDetective;

    @Column(name = "mission_detective_id", updatable = false, insertable = false)
    Long missionDetectiveId;

    String characterIds;
}
