package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.AnswerEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer {
    Long id;
    String type;
    Integer points;
    String text;
    Instant time;
    Long questionId;

    public static Answer toModel(AnswerEntity entity) {
        Answer model = new Answer();
        model.setId(entity.getId());
        model.setText(entity.getText());
        model.setType(entity.getType());
        model.setTime(entity.getTime());
        model.setPoints(entity.getPoints());
        model.setQuestionId(entity.getQuestionId());
        return model;
    }

    public static List<Answer> toModelList(List<AnswerEntity> entities) {
        return entities.stream().map(Answer::toModel).collect(Collectors.toList());
    }
}
