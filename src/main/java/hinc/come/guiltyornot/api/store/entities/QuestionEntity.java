package hinc.come.guiltyornot.api.store.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "question")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String text;

    String title;

    @Builder.Default
    Instant time = Instant.now();

    @ManyToOne
    @JoinColumn(name = "mission_id", referencedColumnName = "id")
    MissionEntity mission;

    @Column(name = "mission_id", updatable = false, insertable = false)
    Long missionId;
}
