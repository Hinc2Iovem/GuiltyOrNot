package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionDetective {
    Long id;

    Boolean withVictim;

    String title;

    String description;

    Integer levelOfDifficulty;

    Integer rewardExp;

    Integer rewardMoney;

    Integer defeatExp;

    Integer defeatMoney;

    String role;

    Long detectiveId;

    Long characterVictimId;

    Long characterGuiltyId;

    List<Long> characterIds;
    public static MissionDetective toModel(MissionDetectiveEntity entity) {
        MissionDetective model = new MissionDetective();
        model.setId(entity.getId());
        model.setDetectiveId(entity.getDetectiveId());
        model.setDescription(entity.getDescription());
        model.setRole(entity.getRole());
        model.setDefeatExp(entity.getDefeatExp());
        model.setDefeatMoney(entity.getDefeatMoney());
        model.setRewardExp(entity.getRewardExp());
        model.setRewardMoney(entity.getRewardMoney());
        model.setLevelOfDifficulty(entity.getLevelOfDifficulty());
        model.setCharacterGuiltyId(entity.getCharacterGuiltyId());
        model.setCharacterVictimId(entity.getCharacterVictimId());
        model.setWithVictim(entity.getWithVictim());
        model.setTitle(entity.getTitle());
        model.setCharacterIds(entity.getCharacterIds());
        return model;
    }
    public static List<MissionDetective> toModelList(List<MissionDetectiveEntity> entities) {
        return entities.stream().map(MissionDetective::toModel).collect(Collectors.toList());
    }
}
