package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.QuestionEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {
    Long id;
    String title;
    String text;
    Instant time;

    public static Question toModel(QuestionEntity entity) {
        Question model = new Question();
        model.setId(entity.getId());
        model.setText(entity.getText());
        model.setTitle(entity.getTitle());
        model.setTime(entity.getTime());
        return model;
    }
}
