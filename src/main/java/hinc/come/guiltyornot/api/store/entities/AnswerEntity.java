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
@Table(name = "answer")
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    String text;

    @NonNull
    String type;

    @Builder.Default
    Integer points = 0;

    @Builder.Default
    Instant time = Instant.now();

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    QuestionEntity question;

    @Column(name = "question_id", updatable = false, insertable = false)
    Long questionId;
}
