package hinc.come.guiltyornot.store.entities;


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
@Table(name = "succeeded_mission")
public class SucceededMissionEntity {
    @Builder.Default
    Integer points = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    MissionEntity mission;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;
}
