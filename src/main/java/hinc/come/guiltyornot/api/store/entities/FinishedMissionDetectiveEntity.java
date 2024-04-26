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
@Table(name = "finished_mission_detective")
public class FinishedMissionDetectiveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "mission_detective_id", referencedColumnName = "id")
    MissionDetectiveEntity mission;

    @Column(name = "mission_detective_id", updatable = false, insertable = false)
    Long missionDetectiveId;

    @ManyToOne
    @JoinColumn(name = "detective_id", referencedColumnName = "id")
    DetectiveEntity detective;

    @Column(name = "detective_id", updatable = false, insertable = false)
    Long detectiveId;
}
