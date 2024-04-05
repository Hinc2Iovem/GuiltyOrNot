package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
import hinc.come.guiltyornot.api.store.entities.MissionEntity;
import hinc.come.guiltyornot.api.store.repositories.MissionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionService {
    @Autowired
    MissionRepository missionRepository;

    @Transactional(readOnly = true)
    public Stream<MissionEntity> getMissions() {
        return missionRepository.streamAllBy();
    }

    public MissionEntity getMissionById(Long missionId) throws NotFoundException {
        Optional<MissionEntity> optionalMission = missionRepository.findById(missionId);
        if (optionalMission.isEmpty()) {
            throw new NotFoundException("Mission with such id wasn't found");
        }
        return optionalMission.get();
    }

    public MissionEntity createMission(MissionEntity missionBody) throws MissingCredentialsException, BadRequestException, UserAlreadyExistException {
        if (
            missionBody.getDescription() == null || missionBody.getDescription().isEmpty() ||
            missionBody.getTitle() == null || missionBody.getTitle().isEmpty() ||
            missionBody.getDefeatExp() == 0 || missionBody.getDefeatMoney() == 0 ||
            missionBody.getRewardExp() == 0 || missionBody.getRewardMoney() == 0
        ) {
            throw new MissingCredentialsException("Description, title, defeatExp, defeatMoney, rewardExp and rewardMoney are required");
        }

        int levelOfDifficulty;
        if(missionBody.getLevelOfDifficulty() != null) {
            levelOfDifficulty = missionBody.getLevelOfDifficulty();
            if(levelOfDifficulty < 1 || levelOfDifficulty > 5){
                throw new BadRequestException("Level of difficulty can not be less than 1 or more than 5!");
            }
        }

        if (missionRepository.findByTitle(missionBody.getTitle()) != null){
            throw new UserAlreadyExistException("Note with such title already exist");
        }

        return missionRepository.save(missionBody);
    }

    public MissionEntity updateMission(MissionEntity missionBody, Long missionId) throws NotFoundException, BadRequestException, UserAlreadyExistException {
        Optional<MissionEntity> missionOptional = missionRepository.findById(missionId);
        if (missionOptional.isEmpty()){
            throw new NotFoundException("Mission with such id doesn't exist " + missionId);
        }
        MissionEntity existingMission = missionOptional.get();

        if(missionBody.getRewardMoney() != null){
            existingMission.setRewardMoney(missionBody.getRewardMoney());
        }
        if(missionBody.getRewardExp() != null){
            existingMission.setRewardExp(missionBody.getRewardExp());
        }
        if(missionBody.getTitle() != null){
            if (missionRepository.findByTitle(missionBody.getTitle()) != null){
                throw new UserAlreadyExistException("Note with such title already exist");
            }
            existingMission.setTitle(missionBody.getTitle());
        }
        if(missionBody.getDescription() != null){
            existingMission.setDescription(missionBody.getDescription());
        }
        if(missionBody.getDefeatExp() != null){
            existingMission.setDefeatExp(missionBody.getDefeatExp());
        }
        if(missionBody.getDefeatMoney() != null){
            existingMission.setDefeatMoney(missionBody.getDefeatMoney());
        }
        if(missionBody.getLevelOfDifficulty() != null){
            if(missionBody.getLevelOfDifficulty() < 1 || missionBody.getLevelOfDifficulty() > 5){
                throw new BadRequestException("Level of difficulty can not be less than 1 or more than 5!");
            }
            existingMission.setLevelOfDifficulty(missionBody.getLevelOfDifficulty());
        }

        return missionRepository.save(existingMission);
    }

    public void deleteMission(Long missionId) throws NotFoundException {
        if(missionRepository.findById(missionId).isEmpty()){
            throw new NotFoundException("Mission with such id doesn't exist");
        }
        missionRepository.deleteById(missionId);
    }
}
