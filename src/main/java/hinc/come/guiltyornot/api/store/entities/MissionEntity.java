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
@Table(name = "mission")
public class MissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String title;

    String description;

    @Builder.Default
    Integer levelOfDifficulty = 1;

    @Builder.Default
    Integer rewardExp = 0;

    @Builder.Default
    Integer rewardMoney = 0;

    @Builder.Default
    Integer defeatExp = 0;

    @Builder.Default
    Integer defeatMoney = 0;

    @Builder.Default
    Instant createdAt = Instant.now();

    @Builder.Default
    @OneToMany(mappedBy = "mission")
    List<FailedMissionEntity> failedMissions = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "mission")
    List<SucceededMissionEntity> succeededMissions = new ArrayList<>();
}
