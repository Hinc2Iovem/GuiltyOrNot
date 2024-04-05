package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.domains.UserRoles;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.store.entities.DetectiveEntity;
import hinc.come.guiltyornot.api.store.entities.GuiltyEntity;
import hinc.come.guiltyornot.api.store.entities.MissionEntity;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
import hinc.come.guiltyornot.api.store.repositories.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MissionRepository missionRepository;
    @Autowired
    DetectiveRepository detectiveRepository;
    @Autowired
    GuiltyRepository guiltyRepository;
    @Autowired
    NotGuiltyRepository notGuiltyRepository;

    public UserEntity updateUserLogin(
            Long userId,
            String userName
    ) throws NotFoundException {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new NotFoundException("User with such id doesn't exist");
        }

        UserEntity existingUser = userOptional.get();

        if(!userName.trim().isEmpty()){
            existingUser.setUsername(userName);
        }

        return userRepository.save(existingUser);

//        if(user.getPassword() != null){
//            existingUser.setPassword(user.getPassword());
//        }
//        if(user.getRole() != null){
//            existingUser.setRole(user.getRole());
//        }
    }

    public UserEntity updateUserPassword(
            Long userId,
            String userPassword
    ) throws NotFoundException {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new NotFoundException("User with such id doesn't exist");
        }

        UserEntity existingUser = userOptional.get();

        if(!userPassword.trim().isEmpty()){
            existingUser.setPassword(userPassword);
        }

        return userRepository.save(existingUser);
    }

    public UserEntity updateUserStates(
            Long userId,
            Long missionId,
            Boolean isFinished
    ) throws NotFoundException {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        Optional<MissionEntity> missionOptional = missionRepository.findById(missionId);

        if (userOptional.isEmpty() || missionOptional.isEmpty()){
            throw new NotFoundException("User with such id doesn't exist");
        }

        UserEntity existingUser = userOptional.get();
        String currentRole = existingUser.getRole().toLowerCase();
        MissionEntity existingMission = missionOptional.get();

        if(isFinished){
            if(Objects.equals(currentRole, "detective")){
                    DetectiveEntity currentDetective = detectiveRepository.findByUserId(userId);
                    currentDetective.setMoney(currentDetective.getMoney() + existingMission.getRewardMoney());
                    currentDetective.setExp(currentDetective.getExp() + existingMission.getRewardExp());

//                    MissionEntity currentDetectiveMission = currentDetective.getUser()
//                            .getMissions().stream()
//                            .filter(m -> m.getId().equals(missionId))
//                            .findFirst()
//                            .orElse(null);
                existingMission.setIsFinished(true);

//                assert currentDetectiveMission != null;
//                currentDetectiveMission.setIsFinished(true);

                missionRepository.save(existingMission);
                detectiveRepository.save(currentDetective);
            } else if(Objects.equals(currentRole, "guilty")){
                GuiltyEntity currentGuilty = guiltyRepository.findByUserId(userId);
                currentGuilty.setMoney(currentGuilty.getMoney() + existingMission.getRewardMoney());
                currentGuilty.setExp(currentGuilty.getExp() + existingMission.getRewardExp());

                existingMission.setIsFinished(true);
//                MissionEntity currentGuiltyMission = currentGuilty.getUser()
//                        .getMissions().stream()
//                        .filter(m -> m.getId().equals(missionId))
//                        .findFirst()
//                        .orElse(null);
//
//                assert currentGuiltyMission != null;
//                currentGuiltyMission.setIsFinished(true);
                missionRepository.save(existingMission);
                guiltyRepository.save(currentGuilty);
            }
        } else {
            if(Objects.equals(currentRole, "detective")){
                DetectiveEntity currentDetective = detectiveRepository.findByUserId(userId);
                boolean moreThanZero = true;
                if(currentDetective.getMoney() == 0
                        || currentDetective.getMoney() < 0
                        || (currentDetective.getMoney() - existingMission.getDefeatMoney()) < 0) {
                    currentDetective.setMoney(0);
                    moreThanZero = false;
                }
                if(currentDetective.getExp() == 0
                        || currentDetective.getExp() < 0
                        || (currentDetective.getExp() - existingMission.getDefeatExp()) < 0) {
                    currentDetective.setExp(0);
                    moreThanZero = false;
                }
                if(moreThanZero) {
                    currentDetective.setMoney(currentDetective.getMoney() - existingMission.getDefeatMoney());
                    currentDetective.setExp(currentDetective.getExp() - existingMission.getDefeatExp());
                }

                detectiveRepository.save(currentDetective);
            } else if(Objects.equals(currentRole, "guilty")){
                GuiltyEntity currentGuilty = guiltyRepository.findByUserId(userId);
                boolean moreThanZero = true;
                if(currentGuilty.getMoney() == 0
                        || currentGuilty.getMoney() < 0
                        || (currentGuilty.getMoney() - existingMission.getDefeatMoney()) < 0) {
                    currentGuilty.setMoney(0);
                    moreThanZero = false;
                }
                if(currentGuilty.getExp() == 0
                        || currentGuilty.getExp() < 0
                        || (currentGuilty.getExp() - existingMission.getDefeatExp()) < 0) {
                    currentGuilty.setExp(0);
                    moreThanZero = false;
                }
                if(moreThanZero){
                    currentGuilty.setMoney(currentGuilty.getMoney() - existingMission.getDefeatMoney());
                    currentGuilty.setExp(currentGuilty.getExp() - existingMission.getDefeatExp());
                }
                guiltyRepository.save(currentGuilty);
            }
        }

        return existingUser;
    }

    public Long deleteUser(Long userId) throws NotFoundException {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("User with such id doesn't exist");
        }
        userRepository.deleteById(userId);
        return userId;
    }
}
