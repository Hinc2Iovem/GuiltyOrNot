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
@Table(name = "not_guilty")
public class NotGuiltyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Builder.Default
    Integer exp = 0;

    @Builder.Default
    Integer money = 0;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;
}
