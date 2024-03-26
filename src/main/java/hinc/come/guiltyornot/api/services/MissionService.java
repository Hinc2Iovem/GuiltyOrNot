package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentials;
import hinc.come.guiltyornot.api.models.Mission;
import hinc.come.guiltyornot.store.entities.MissionEntity;
import hinc.come.guiltyornot.store.repositories.MissionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionService {
    @Autowired
    MissionRepository missionRepository;

    public Stream<MissionEntity> getMissions() {
        return missionRepository.streamAllBy();
    }

    public MissionEntity createMission(MissionEntity missionBody) throws MissingCredentials, BadRequestException {
        if (
            missionBody.getDescription() == null || missionBody.getDescription().isEmpty() ||
            missionBody.getTitle() == null || missionBody.getTitle().isEmpty() ||
            missionBody.getDefeatExp() == 0 || missionBody.getDefeatMoney() == 0 ||
            missionBody.getRewardExp() == 0 || missionBody.getRewardMoney() == 0
        ) {
            throw new MissingCredentials("Description, title, defeatExp, defeatMoney, rewardExp and rewardMoney are required");
        }

        boolean duplicate = missionRepository.findByTitle(missionBody.getTitle());

        if(duplicate){
            throw new BadRequestException("Mission with such title already exists");
        }

        return missionRepository.save(missionBody);
    }
}
