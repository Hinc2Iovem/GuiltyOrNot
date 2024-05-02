package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.CharacterEntity;
import hinc.come.guiltyornot.api.store.entities.CharacterGuiltyEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterGuilty {
    Long id;

    Long characterEntityId;

    Long missionId;

    public static CharacterGuilty toModel(CharacterGuiltyEntity entity) {
        CharacterGuilty model = new CharacterGuilty();
        model.setId(entity.getId());
        model.setCharacterEntityId(entity.getCharacterEntityId());
        model.setMissionId(entity.getMissionId());
        return model;
    }
    public static List<CharacterGuilty> toModelList(List<CharacterGuiltyEntity> entities) {
        return entities.stream().map(CharacterGuilty::toModel).collect(Collectors.toList());
    }
}
