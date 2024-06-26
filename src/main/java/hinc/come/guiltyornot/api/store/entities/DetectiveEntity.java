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
@Table(name = "detective")
public class DetectiveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Builder.Default
    Integer exp = 0;

    @Builder.Default
    Integer money = 0;

    String imgUrl;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;

    @Column(name = "user_id", updatable = false, insertable = false)
    Long userId;

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "detective_id", referencedColumnName = "id")
    List<FinishedMissionDetectiveEntity> finishedMissionsDetective = new ArrayList<>();
}
