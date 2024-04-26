package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
import hinc.come.guiltyornot.api.store.entities.MissionDetectiveEntity;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
import hinc.come.guiltyornot.api.store.repositories.MissionDetectiveRepository;
import hinc.come.guiltyornot.api.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionDetectiveService {
    @Autowired
    MissionDetectiveRepository missionDetectiveRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<MissionDetectiveEntity> getMissions() {
        return missionDetectiveRepository.findAllBy();
    }

    public MissionDetectiveEntity getMissionById(Long missionId) throws NotFoundException {
        Optional<MissionDetectiveEntity> optionalMission = missionDetectiveRepository.findById(missionId);
        if (optionalMission.isEmpty()) {
            throw new NotFoundException("Mission with such id wasn't found");
        }
        return optionalMission.get();
    }

    public MissionDetectiveEntity createMission(
            MissionDetectiveEntity missionBody,
            Long userId
    ) throws MissingCredentialsException, BadRequestException, UserAlreadyExistException, NotFoundException {
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new NotFoundException("User with such id doesn't exist");
        }
        UserEntity existingUser = user.get();

        if(existingUser.getRole().equals("detective") || existingUser.getRole().equals("admin")){
            if(missionBody.getDescription() == null || missionBody.getDescription().isEmpty() ||
                missionBody.getTitle() == null || missionBody.getTitle().isEmpty() ||
                missionBody.getRole().trim().isEmpty() || missionBody.getCharacters().isEmpty() ||
                missionBody.getCharacterGuilty() == null
            ) {
                throw new MissingCredentialsException("Description, title, characters, guilty and role(detective) are required");
            }
            if(existingUser.getRole().equals("admin")){
                if(missionBody.getDefeatExp() == 0 || missionBody.getDefeatMoney() == 0 ||
                        missionBody.getRewardExp() == 0 || missionBody.getRewardMoney() == 0){
                    throw new MissingCredentialsException("Description, title, defeatExp, defeatMoney, rewardExp, role(detective, guilty) and rewardMoney are required");
                }
            }
        }

        int levelOfDifficulty;
        if(missionBody.getLevelOfDifficulty() != null) {
            levelOfDifficulty = missionBody.getLevelOfDifficulty();
            if(levelOfDifficulty < 1 || levelOfDifficulty > 5){
                throw new BadRequestException("Level of difficulty can not be less than 1 or more than 5!");
            }
        }

        if (missionDetectiveRepository.findByTitle(missionBody.getTitle()) != null){
            throw new UserAlreadyExistException("Mission with such title already exist");
        }

        if(!existingUser.getRole().equals("admin")){
            if(!missionBody.getRole().equals(existingUser.getRole())){
                throw new BadRequestException("Your role should be of a type which you are trying to assign the mission!");
            }
        }

        return missionDetectiveRepository.save(missionBody);
    }

    public MissionDetectiveEntity updateMission(
            MissionDetectiveEntity missionBody,
            Long missionId,
            Long userId
    ) throws NotFoundException, BadRequestException, UserAlreadyExistException, MissingCredentialsException {
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new NotFoundException("User with such id doesn't exist");
        }
        UserEntity existingUser = user.get();

        Optional<MissionDetectiveEntity> missionOptional = missionDetectiveRepository.findById(missionId);
        if (missionOptional.isEmpty()){
            throw new NotFoundException("Mission with such id doesn't exist " + missionId);
        }


        MissionDetectiveEntity existingMission = missionOptional.get();

        if(existingUser.getRole().equals("admin")){
            if(missionBody.getRewardMoney() != null){
                existingMission.setRewardMoney(missionBody.getRewardMoney());
            }
            if(missionBody.getRewardExp() != null){
                existingMission.setRewardExp(missionBody.getRewardExp());
            }
            if(missionBody.getDefeatExp() != null){
                existingMission.setDefeatExp(missionBody.getDefeatExp());
            }
            if(missionBody.getDefeatMoney() != null){
                existingMission.setDefeatMoney(missionBody.getDefeatMoney());
            }
        }

        if(missionBody.getWithVictim() != null){
            existingMission.setWithVictim(missionBody.getWithVictim());
            existingMission.setCharacterVictim(missionBody.getCharacterVictim());
            existingMission.setCharacterVictimId(missionBody.getCharacterVictimId());
        }

        if(missionBody.getTitle() != null){
            if (missionDetectiveRepository.findByTitle(missionBody.getTitle()) != null){
                throw new UserAlreadyExistException("Mission with such title already exist");
            }
            existingMission.setTitle(missionBody.getTitle());
        }
        if(missionBody.getDescription() != null){
            existingMission.setDescription(missionBody.getDescription());
        }


        if(missionBody.getLevelOfDifficulty() != null){
            if(missionBody.getLevelOfDifficulty() < 1 || missionBody.getLevelOfDifficulty() > 5){
                throw new BadRequestException("Level of difficulty can not be less than 1 or more than 5!");
            }
            existingMission.setLevelOfDifficulty(missionBody.getLevelOfDifficulty());
        }


        return missionDetectiveRepository.save(existingMission);
    }

    public void deleteMission(Long missionId) throws NotFoundException {
        Optional<MissionDetectiveEntity> missionOptional = missionDetectiveRepository.findById(missionId);
        if (missionOptional.isEmpty()) {
            throw new NotFoundException("Mission with such id doesn't exist");
        }

        missionDetectiveRepository.deleteById(missionId);
    }
}
