package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
import hinc.come.guiltyornot.api.store.entities.*;
import hinc.come.guiltyornot.api.store.repositories.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    MissionDetectiveRepository missionDetectiveRepository;
    @Autowired
    MissionGuiltyRepository missionGuiltyRepository;
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
        Optional<MissionDetectiveEntity> missionDetectiveOptional = missionDetectiveRepository.findById(missionId);
        Optional<MissionGuiltyEntity> missionGuiltyOptional = missionGuiltyRepository.findById(missionId);


        if (userOptional.isEmpty()){
            throw new NotFoundException("User with such id doesn't exist");
        }

        UserEntity existingUser = userOptional.get();
        String currentRole = existingUser.getRole().toLowerCase();
        if(currentRole.equals("detective")){
            if(missionDetectiveOptional.isEmpty()){
                throw new NotFoundException("Mission with such id doesn't exist");
            }
        }else if(currentRole.equals("guilty")){
            if(missionGuiltyOptional.isEmpty()){
                throw new NotFoundException("Mission with such id doesn't exist");
            }
        }

        MissionDetectiveEntity missionDetective = missionDetectiveOptional.get();
        MissionGuiltyEntity missionGuilty = missionGuiltyOptional.get();

        if(isFinished){
            if(currentRole.equals("detective")){
                    DetectiveEntity currentDetective = detectiveRepository.findByUserId(userId);
                    currentDetective.setMoney(currentDetective.getMoney() + missionDetective.getRewardMoney());
                    currentDetective.setExp(currentDetective.getExp() + missionDetective.getRewardExp());

                FinishedMissionDetectiveEntity existingFinishedMission = finishedMissionDetectiveRepository.findByMissionIdAndDetectiveId(missionDetective.getId(), existingUser.getId());
                if(existingFinishedMission == null){
                    FinishedMissionDetectiveEntity currentFinishedMission = new FinishedMissionDetectiveEntity();
                    currentFinishedMission.setMission(missionDetective);
                    currentFinishedMission.setDetective(currentDetective);

                    finishedMissionDetectiveRepository.save(currentFinishedMission);
                }
                detectiveRepository.save(currentDetective);
            } else if(currentRole.equals("guilty")){
                GuiltyEntity currentGuilty = guiltyRepository.findByUserId(userId);
                currentGuilty.setMoney(currentGuilty.getMoney() + missionGuilty.getRewardMoney());
                currentGuilty.setExp(currentGuilty.getExp() + missionGuilty.getRewardExp());

                FinishedMissionGuiltyEntity existingFinishedMission = finishedMissionGuiltyRepository.findByMissionIdAndGuiltyId(missionGuilty.getId(), existingUser.getId());
                if(existingFinishedMission == null){
                    FinishedMissionGuiltyEntity currentFinishedMission = new FinishedMissionGuiltyEntity();
                    currentFinishedMission.setMission(missionGuilty);
                    currentFinishedMission.setGuilty(currentGuilty);

                    finishedMissionGuiltyRepository.save(currentFinishedMission);
                }

                guiltyRepository.save(currentGuilty);
            }
        } else {
            if(currentRole.equals("detective")){
                DetectiveEntity currentDetective = detectiveRepository.findByUserId(userId);
                boolean moreThanZero = true;
                if(currentDetective.getMoney() == 0
                        || currentDetective.getMoney() < 0
                        || (currentDetective.getMoney() - missionDetective.getDefeatMoney()) < 0) {
                    currentDetective.setMoney(0);
                    moreThanZero = false;
                }
                if(currentDetective.getExp() == 0
                        || currentDetective.getExp() < 0
                        || (currentDetective.getExp() - missionDetective.getDefeatExp()) < 0) {
                    currentDetective.setExp(0);
                    moreThanZero = false;
                }
                if(moreThanZero) {
                    currentDetective.setMoney(currentDetective.getMoney() - missionDetective.getDefeatMoney());
                    currentDetective.setExp(currentDetective.getExp() - missionDetective.getDefeatExp());
                }

                detectiveRepository.save(currentDetective);
            } else if(currentRole.equals("guilty")){
                GuiltyEntity currentGuilty = guiltyRepository.findByUserId(userId);
                boolean moreThanZero = true;
                if(currentGuilty.getMoney() == 0
                        || currentGuilty.getMoney() < 0
                        || (currentGuilty.getMoney() - missionGuilty.getDefeatMoney()) < 0) {
                    currentGuilty.setMoney(0);
                    moreThanZero = false;
                }
                if(currentGuilty.getExp() == 0
                        || currentGuilty.getExp() < 0
                        || (currentGuilty.getExp() - missionGuilty.getDefeatExp()) < 0) {
                    currentGuilty.setExp(0);
                    moreThanZero = false;
                }
                if(moreThanZero){
                    currentGuilty.setMoney(currentGuilty.getMoney() - missionGuilty.getDefeatMoney());
                    currentGuilty.setExp(currentGuilty.getExp() - missionGuilty.getDefeatExp());
                }
                guiltyRepository.save(currentGuilty);
            }
        }

        return existingUser;
    }

    public void deleteUser(Long userId) throws NotFoundException {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("User with such id doesn't exist");
        }
        List<FinishedMissionDetectiveEntity> finishedMissionsDetective = finishedMissionDetectiveRepository.findAllByDetectiveId(userId);
        List<FinishedMissionGuiltyEntity> finishedMissionsGuilty = finishedMissionGuiltyRepository.findAllByGuiltyId(userId);
        if(!finishedMissionsGuilty.isEmpty()){
            finishedMissionGuiltyRepository.deleteAll();
        }
        if(!finishedMissionsDetective.isEmpty()){
            finishedMissionDetectiveRepository.deleteAll();
        }
        List<MissionDetectiveEntity> missionsDetective = missionDetectiveRepository.findAllByDetectiveId(userId);
        if(!missionsDetective.isEmpty()){
            for(MissionDetectiveEntity m : missionsDetective){
                m.setDetective(null);
                m.setDetectiveId(null);
            }
        }
        List<MissionGuiltyEntity> missionsGuilty = missionGuiltyRepository.findAllByGuiltyId(userId);
        if(!missionsDetective.isEmpty()){
            for(MissionGuiltyEntity m : missionsGuilty){
                m.setGuiltyId(null);
                m.setGuilty(null);
            }
        }
        detectiveRepository.deleteById(userId);
        guiltyRepository.deleteById(userId);
        userRepository.deleteById(userId);
    }
}
