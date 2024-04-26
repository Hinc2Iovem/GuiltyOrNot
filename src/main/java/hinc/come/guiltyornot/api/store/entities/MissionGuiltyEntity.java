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
@Table(name = "mission_guilty")
public class MissionGuiltyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String title;

    String description;

    @Builder.Default
    Integer levelOfDifficulty = 1;

    Integer rewardExp;

    Integer rewardMoney;

    Integer defeatExp;

    Integer defeatMoney;

    String role;

    @OneToOne
    @JoinColumn(name = "mission_id", referencedColumnName = "id")
    MissionEntity mission;

    @Column(name = "mission_id", updatable = false, insertable = false)
    Long missionId;
}
