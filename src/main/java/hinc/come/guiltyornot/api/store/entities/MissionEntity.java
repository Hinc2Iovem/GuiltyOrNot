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

    Integer rewardExp;

    Integer rewardMoney;

    Integer defeatExp;

    Integer defeatMoney;

    @Builder.Default
    Instant createdAt = Instant.now();

    String role;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;

    @Column(name = "user_id", updatable = false, insertable = false)
    Long userId;
}
