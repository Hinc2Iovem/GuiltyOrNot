package hinc.come.guiltyornot.api.factories;

import hinc.come.guiltyornot.api.dto.MissionDto;
import hinc.come.guiltyornot.store.entities.MissionEntity;
import org.springframework.stereotype.Component;

@Component
public class MissionDtoFactory {

    public MissionDto makeMissionDto(MissionEntity entity){

        return MissionDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .defeat_exp(entity.getDefeat_exp())
                .defeat_money(entity.getDefeat_money())
                .levelOfDifficulty(entity.getLevelOfDifficulty())
                .reward_exp(entity.getReward_exp())
                .reward_money(entity.getReward_money())
                .build();
    }
}
