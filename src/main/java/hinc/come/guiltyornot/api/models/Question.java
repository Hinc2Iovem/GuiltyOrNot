package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.QuestionEntity;
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
public class Question {
    Long id;
    Long missionId;
    String title;
    Instant time;

    public static Question toModel(QuestionEntity entity) {
        Question model = new Question();
        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setTime(entity.getTime());
        model.setMissionId(entity.getMissionId());
        return model;
    }

    public static List<Question> toModelList(List<QuestionEntity> entities) {
        return entities.stream().map(Question::toModel).collect(Collectors.toList());
    }
}
