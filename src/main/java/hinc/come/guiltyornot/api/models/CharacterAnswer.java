package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.CharacterAnswerEntity;
import hinc.come.guiltyornot.api.store.entities.CharacterQuestionEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterAnswer {
    Long id;
    String text;
    Long characterQuestionId;
    public static CharacterAnswer toModel(CharacterAnswerEntity entity) {
        CharacterAnswer model = new CharacterAnswer();
        model.setId(entity.getId());
        model.setText(entity.getText());
        model.setCharacterQuestionId(entity.getCharacterQuestionId());
        return model;
    }
    public static List<CharacterAnswer> toModelList(List<CharacterAnswerEntity> entities) {
        return entities.stream().map(CharacterAnswer::toModel).collect(Collectors.toList());
    }
}
