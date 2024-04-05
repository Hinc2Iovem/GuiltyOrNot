package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.domains.UserRoles;
import hinc.come.guiltyornot.api.exceptions.BadRequestException;
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
            Long userId,
            String currentRole
    ) throws NotFoundException, BadRequestException {
        UserEntity user = userRepository.findById(userId).orElse(null);
        MissionEntity mission = missionRepository.findById(missionId).orElse(null);

        if (user == null || mission == null) {
            throw new NotFoundException("Mission or user with such id doesn't exist");
        }

        if(currentRole.trim().isEmpty()){
            throw new NotFoundException("role is required.(detective, guilty)");
        }

        if(mission.getRole().equals("detective")){
            if(!currentRole.equals("detective")){
               throw new BadRequestException("You supposed to be a role of detective to finish detective missions.");
            }
            FinishedMissionDetectiveEntity existingFinishedMission = finishedMissionDetectiveRepository.findByMissionIdAndUserId(mission.getId(), user.getId());
            if(existingFinishedMission != null){
                return "User with id " + user.getId() + " finished mission with id " + mission.getId() + " repetition";
            }

            FinishedMissionDetectiveEntity currentFinishedMission = new FinishedMissionDetectiveEntity();
            currentFinishedMission.setMission(mission);
            currentFinishedMission.setUser(user);

            finishedMissionDetectiveRepository.save(currentFinishedMission);
        } else if (mission.getRole().equals("guilty")) {
            if(!currentRole.equals("guilty")){
                throw new BadRequestException("You supposed to be a role of guilty to finish guilty missions." + currentRole);
            }
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
