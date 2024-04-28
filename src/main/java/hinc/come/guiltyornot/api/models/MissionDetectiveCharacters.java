package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.DetectiveImagesEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionDetectiveCharacters {
    Long id;
    Long missionDetectiveId;
    String characterIds;

    public static MissionDetectiveCharacters toModel(MissionDetectiveCharacters entity) {
        MissionDetectiveCharacters model = new MissionDetectiveCharacters();
        model.setId(entity.getId());
        model.setMissionDetectiveId(entity.getMissionDetectiveId());
        model.setCharacterIds(entity.getCharacterIds());
        return model;
    }
    public static List<MissionDetectiveCharacters> toModelList(List<MissionDetectiveCharacters> entities) {
        return entities.stream().map(MissionDetectiveCharacters::toModel).collect(Collectors.toList());
    }
}
