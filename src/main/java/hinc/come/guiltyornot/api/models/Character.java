package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Character {
    Long id;

    String description;

    String name;

    String hairColor;

    String gender;

    Integer age;

    Integer levelOfDifficulty;

    String feature;

    Long detectiveId;

    public static Character toModel(CharacterEntity entity) {
        Character model = new Character();
        model.setId(entity.getId());
        model.setDescription(entity.getDescription());
        model.setAge(entity.getAge());
        model.setGender(entity.getGender());
        model.setFeature(entity.getFeature());
        model.setDetectiveId(entity.getDetectiveId());
        model.setName(entity.getName());
        model.setLevelOfDifficulty(entity.getLevelOfDifficulty());
        model.setHairColor(entity.getHairColor());
        return model;
    }
    public static List<Character> toModelList(List<CharacterEntity> entities) {
        return entities.stream().map(Character::toModel).collect(Collectors.toList());
    }
}