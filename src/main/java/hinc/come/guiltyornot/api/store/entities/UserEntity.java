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
    Integer exp = 0;

    @Builder.Default
    Integer money = 0;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    List<FailedMissionEntity> failedMissions = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    List<SucceededMissionEntity> succeededMissions = new ArrayList<>();
}
