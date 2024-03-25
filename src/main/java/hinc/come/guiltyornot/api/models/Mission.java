package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.store.entities.MissionEntity;
import hinc.come.guiltyornot.store.entities.UserEntity;
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
        model.setDefeatExp(entity.getDefeat_exp());
        model.setDefeatMoney(entity.getDefeat_money());
        model.setRewardExp(entity.getReward_exp());
        model.setRewardMoney(entity.getReward_money());
        model.setLevelOfDifficulty(entity.getLevelOfDifficulty());
        return model;
    }

}
