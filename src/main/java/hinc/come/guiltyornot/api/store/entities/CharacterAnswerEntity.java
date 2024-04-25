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
@Table(name = "character_answer")
public class CharacterAnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String text;
    @OneToOne
    @JoinColumn(name = "character_question_id", referencedColumnName = "id")
    CharacterQuestionEntity characterQuestion;

    @Column(name = "character_question_id", updatable = false, insertable = false)
    Long characterQuestionId;
}
