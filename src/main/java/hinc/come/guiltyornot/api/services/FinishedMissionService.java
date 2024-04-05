package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.store.entities.FinishedMissionDetectiveEntity;
import hinc.come.guiltyornot.api.store.entities.FinishedMissionGuiltyEntity;
import hinc.come.guiltyornot.api.store.entities.MissionEntity;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
import hinc.come.guiltyornot.api.store.repositories.FinishedMissionDetectiveRepository;
import hinc.come.guiltyornot.api.store.repositories.FinishedMissionGuiltyRepository;
import hinc.come.guiltyornot.api.store.repositories.MissionRepository;
import hinc.come.guiltyornot.api.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
public class FinishedMissionService {
    @Autowired
    FinishedMissionDetectiveRepository finishedMissionDetectiveRepository;
    @Autowired
    FinishedMissionGuiltyRepository finishedMissionGuiltyRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MissionRepository missionRepository;

    public String addFinishedMission(
            Long missionId,
            Long userId
    ) throws NotFoundException {
        UserEntity user = userRepository.findById(userId).orElse(null);
        MissionEntity mission = missionRepository.findById(missionId).orElse(null);

        if (user == null || mission == null) {
            throw new NotFoundException("Mission or user with such id doesn't exist");
        }

        if(mission.getRole().equals("detective")){
            FinishedMissionDetectiveEntity existingFinishedMission = finishedMissionDetectiveRepository.findByMissionIdAndUserId(mission.getId(), user.getId());
            if(existingFinishedMission != null){
                return "User with id " + user.getId() + " finished mission with id " + mission.getId() + " repetition";
            }

            FinishedMissionDetectiveEntity currentFinishedMission = new FinishedMissionDetectiveEntity();
            currentFinishedMission.setMission(mission);
            currentFinishedMission.setUser(user);

            finishedMissionDetectiveRepository.save(currentFinishedMission);
        } else if (mission.getRole().equals("guilty")) {
            FinishedMissionGuiltyEntity existingFinishedMission = finishedMissionGuiltyRepository.findByMissionIdAndUserId(mission.getId(), user.getId());
            if(existingFinishedMission != null){
                return "User with id " + user.getId() + " finished mission with id " + mission.getId() + " repetition";
            }

            FinishedMissionGuiltyEntity currentFinishedMission = new FinishedMissionGuiltyEntity();
            currentFinishedMission.setMission(mission);
            currentFinishedMission.setUser(user);

            finishedMissionGuiltyRepository.save(currentFinishedMission);
        }

        return "User with id " + user.getId() + " finished mission with id " + mission.getId();
    }
}
