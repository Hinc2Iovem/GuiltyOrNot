package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.MissionEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mission {
    Long id;
    String title;
    String description;
    Integer defeatExp;
    Integer defeatMoney;
    Integer rewardExp;
    Integer rewardMoney;
    Integer levelOfDifficulty;
    public static Mission toModel(MissionEntity entity) {
        Mission model = new Mission();
        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setDescription(entity.getDescription());
        model.setDefeatExp(entity.getDefeatExp());
        model.setDefeatMoney(entity.getDefeatMoney());
        model.setRewardExp(entity.getRewardExp());
        model.setRewardMoney(entity.getRewardMoney());
        model.setLevelOfDifficulty(entity.getLevelOfDifficulty());
        return model;
    }

}
