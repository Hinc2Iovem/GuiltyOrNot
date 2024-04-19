package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.domains.UserRoles;
import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
import hinc.come.guiltyornot.api.models.User;
import hinc.come.guiltyornot.api.store.entities.*;
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
    FinishedMissionDetectiveRepository finishedMissionDetectiveRepository;
    @Autowired
    FinishedMissionGuiltyRepository finishedMissionGuiltyRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MissionRepository missionRepository;
    @Autowired
    DetectiveRepository detectiveRepository;
    @Autowired
    GuiltyRepository guiltyRepository;


    public UserEntity updateUserLogin(
            Long userId,
            UserEntity user
    ) throws NotFoundException, BadRequestException, MissingCredentialsException, UserAlreadyExistException {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new NotFoundException("User with such id doesn't exist");
        }

        UserEntity existingUser = userOptional.get();

        if(user.getPassword() == null || user.getPassword().trim().isEmpty()){
               throw new MissingCredentialsException("Password is expected too!");
        }

        if(!existingUser.getPassword().equals(user.getPassword())){
              throw new BadRequestException("Password don't match");
        }

        if(user.getUsername() != null && !user.getUsername().trim().isEmpty()){
            UserEntity duplicateUser = userRepository.findByUsername(user.getUsername());
            if(duplicateUser != null && !Objects.equals(duplicateUser.getId(), userId)){
                throw new UserAlreadyExistException("User with such username already exists");
            }
            existingUser.setUsername(user.getUsername());
        }

        return userRepository.save(existingUser);
    }

    public UserEntity updateUserPassword(
            Long userId,
            UserEntity user
    ) throws NotFoundException, MissingCredentialsException, BadRequestException {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new NotFoundException("User with such id doesn't exist");
        }

        UserEntity existingUser = userOptional.get();
        if(user.getUsername() == null || user.getUsername().trim().isEmpty()){
            throw new MissingCredentialsException("Username is expected too!");
        }

        if(!existingUser.getUsername().equals(user.getUsername())){
            throw new BadRequestException("Usernames don't match");
        }

        if(user.getPassword() != null && !user.getPassword().trim().isEmpty()){
            existingUser.setPassword(user.getPassword());
        }

        return userRepository.save(existingUser);
    }

    public UserEntity updateUserRole(
            Long userId,
            String role
    ) throws NotFoundException {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new NotFoundException("User with such id doesn't exist");
        }

        UserEntity existingUser = userOptional.get();

        existingUser.setRole(role);

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
            throw new NotFoundException("User or mission with such id doesn't exist");
        }

        UserEntity existingUser = userOptional.get();
        String currentRole = existingUser.getRole().toLowerCase();
        MissionEntity existingMission = missionOptional.get();

        if(isFinished){
            if(Objects.equals(currentRole, "detective")){
                    DetectiveEntity currentDetective = detectiveRepository.findByUserId(userId);
                    currentDetective.setMoney(currentDetective.getMoney() + existingMission.getRewardMoney());
                    currentDetective.setExp(currentDetective.getExp() + existingMission.getRewardExp());

                FinishedMissionDetectiveEntity existingFinishedMission = finishedMissionDetectiveRepository.findByMissionIdAndUserId(existingMission.getId(), existingUser.getId());
                if(existingFinishedMission == null){
                    FinishedMissionDetectiveEntity currentFinishedMission = new FinishedMissionDetectiveEntity();
                    currentFinishedMission.setMission(existingMission);
                    currentFinishedMission.setUser(existingUser);

                    finishedMissionDetectiveRepository.save(currentFinishedMission);
                }
                missionRepository.save(existingMission);
                detectiveRepository.save(currentDetective);
            } else if(Objects.equals(currentRole, "guilty")){
                GuiltyEntity currentGuilty = guiltyRepository.findByUserId(userId);
                currentGuilty.setMoney(currentGuilty.getMoney() + existingMission.getRewardMoney());
                currentGuilty.setExp(currentGuilty.getExp() + existingMission.getRewardExp());

                FinishedMissionGuiltyEntity existingFinishedMission = finishedMissionGuiltyRepository.findByMissionIdAndUserId(existingMission.getId(), existingUser.getId());
                if(existingFinishedMission == null){
                    FinishedMissionGuiltyEntity currentFinishedMission = new FinishedMissionGuiltyEntity();
                    currentFinishedMission.setMission(existingMission);
                    currentFinishedMission.setUser(existingUser);

                    finishedMissionGuiltyRepository.save(currentFinishedMission);
                }
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
