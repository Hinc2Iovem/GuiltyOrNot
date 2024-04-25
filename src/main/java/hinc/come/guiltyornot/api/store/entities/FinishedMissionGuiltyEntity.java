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
@Table(name = "finished_mission_guilty")
public class FinishedMissionGuiltyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "mission_id", referencedColumnName = "id")
    MissionEntity mission;

    @Column(name = "mission_id", updatable = false, insertable = false)
    Long missionId;

    @ManyToOne
    @JoinColumn(name = "guilty_id", referencedColumnName = "id")
    GuiltyEntity guilty;

    @Column(name = "guilty_id", updatable = false, insertable = false)
    Long guiltyId;
}
