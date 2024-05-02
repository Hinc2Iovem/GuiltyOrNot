package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.CharacterGuiltyEntity;
import hinc.come.guiltyornot.api.store.entities.CharacterVictimEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterVictim {
    Long id;

    Long characterEntityId;

    Long missionId;

    public static CharacterVictim toModel(CharacterVictimEntity entity) {
        CharacterVictim model = new CharacterVictim();
        model.setId(entity.getId());
        model.setCharacterEntityId(entity.getCharacterEntityId());
        model.setMissionId(entity.getMissionId());
        return model;
    }
    public static List<CharacterVictim> toModelList(List<CharacterVictimEntity> entities) {
        return entities.stream().map(CharacterVictim::toModel).collect(Collectors.toList());
    }
}
