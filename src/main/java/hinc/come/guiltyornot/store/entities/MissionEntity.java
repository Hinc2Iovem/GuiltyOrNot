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
@Table(name = "mission")
public class MissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Column(unique = true)
    String title;

    @NonNull
    String description;

    @Builder.Default
    Integer levelOfDifficulty = 1;

    @Builder.Default
    Integer reward_exp = 0;

    @Builder.Default
    Integer reward_money = 0;

    @Builder.Default
    Integer defeat_exp = 0;

    @Builder.Default
    Integer defeat_money = 0;

    @Builder.Default
    Instant createdAt = Instant.now();

    @Builder.Default
    @OneToMany(mappedBy = "mission")
    List<FailedMissionEntity> failedMissions = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "mission")
    List<SucceededMissionEntity> succeededMissions = new ArrayList<>();
}
