package hinc.come.guiltyornot.api.store.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String username;

    String role;

    String password;

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    List<FinishedMissionDetectiveEntity> finishedMissionsDetective = new ArrayList<>();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    List<FinishedMissionGuiltyEntity> finishedMissionsGuilty = new ArrayList<>();
}
