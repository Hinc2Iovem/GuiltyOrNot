package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.CharacterEntity;
import hinc.come.guiltyornot.api.store.entities.CharacterQuestionEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterQuestion {
    Long id;
    String text;
    Long characterEntityId;
    public static CharacterQuestion toModel(CharacterQuestionEntity entity) {
        CharacterQuestion model = new CharacterQuestion();
        model.setId(entity.getId());
        model.setText(entity.getText());
        model.setCharacterEntityId(entity.getCharacterEntityId());
        return model;
    }
    public static List<CharacterQuestion> toModelList(List<CharacterQuestionEntity> entities) {
        return entities.stream().map(CharacterQuestion::toModel).collect(Collectors.toList());
    }
}
