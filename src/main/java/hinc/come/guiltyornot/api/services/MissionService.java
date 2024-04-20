package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.domains.UserRoles;
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

    @Transactional(readOnly = true)
    public Stream<MissionEntity> getMissionsByRole(
            String role
    ) {
        return missionRepository.streamAllByRole(role);
    }

    public MissionEntity getMissionById(Long missionId) throws NotFoundException {
        Optional<MissionEntity> optionalMission = missionRepository.findById(missionId);
        if (optionalMission.isEmpty()) {
            throw new NotFoundException("Mission with such id wasn't found");
        }
        return optionalMission.get();
    }

    public MissionEntity createMission(
            MissionEntity missionBody,
            String role
    ) throws MissingCredentialsException, BadRequestException, UserAlreadyExistException {
        if(role == null || role.trim().isEmpty()){
            throw new MissingCredentialsException("Role is required!");
        }

        if(role.equals("detective") || role.equals("guilty")){
            if (
                    missionBody.getDescription() == null || missionBody.getDescription().isEmpty() ||
                            missionBody.getTitle() == null || missionBody.getTitle().isEmpty() ||
                            missionBody.getRole().trim().isEmpty()
            ) {
                throw new MissingCredentialsException("Description, title, and role(detective, guilty) are required");
            }
        }else if(role.equals("admin")){
            if (
                    missionBody.getDescription() == null || missionBody.getDescription().isEmpty() ||
                            missionBody.getTitle() == null || missionBody.getTitle().isEmpty() ||
                            missionBody.getDefeatExp() == 0 || missionBody.getDefeatMoney() == 0 ||
                            missionBody.getRewardExp() == 0 || missionBody.getRewardMoney() == 0 ||
                            missionBody.getRole().trim().isEmpty()
            ) {
                throw new MissingCredentialsException("Description, title, defeatExp, defeatMoney, rewardExp, role(detective, guilty) and rewardMoney are required");
            }
        }

        int levelOfDifficulty;
        if(missionBody.getLevelOfDifficulty() != null) {
            levelOfDifficulty = missionBody.getLevelOfDifficulty();
            if(levelOfDifficulty < 1 || levelOfDifficulty > 5){
                throw new BadRequestException("Level of difficulty can not be less than 1 or more than 5!");
            }
        }

        if (missionRepository.findByTitle(missionBody.getTitle()) != null){
            throw new UserAlreadyExistException("Mission with such title already exist");
        }

        return missionRepository.save(missionBody);
    }

    public MissionEntity updateMission(
            MissionEntity missionBody,
            Long missionId,
            String role
    ) throws NotFoundException, BadRequestException, UserAlreadyExistException, MissingCredentialsException {
        if(role == null || role.trim().isEmpty()){
            throw new MissingCredentialsException("Role is required!");
        }

        Optional<MissionEntity> missionOptional = missionRepository.findById(missionId);
        if (missionOptional.isEmpty()){
            throw new NotFoundException("Mission with such id doesn't exist " + missionId);
        }
        MissionEntity existingMission = missionOptional.get();

        if(role.equals("admin")){
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

        if(missionBody.getTitle() != null){
            if (missionRepository.findByTitle(missionBody.getTitle()) != null){
                throw new UserAlreadyExistException("Note with such title already exist");
            }
            existingMission.setTitle(missionBody.getTitle());
        }
        if(missionBody.getDescription() != null){
            existingMission.setDescription(missionBody.getDescription());
        }

        if(missionBody.getRole() != null){
            existingMission.setRole(missionBody.getRole());
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
