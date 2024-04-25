package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.domains.UserRoles;
import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
import hinc.come.guiltyornot.api.models.Mission;
import hinc.come.guiltyornot.api.models.User;
import hinc.come.guiltyornot.api.store.entities.MissionEntity;
import hinc.come.guiltyornot.api.store.entities.MissionGuiltyEntity;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
import hinc.come.guiltyornot.api.store.repositories.MissionGuiltyRepository;
import hinc.come.guiltyornot.api.store.repositories.MissionRepository;
import hinc.come.guiltyornot.api.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionService {
    @Autowired
    MissionRepository missionRepository;
    @Autowired
    MissionGuiltyRepository missionGuiltyRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Mission> getMissions() {
        return Mission.toModelList(missionRepository.findAllBy());
    }

    @Transactional(readOnly = true)
    public List<Mission> getMissionsByRole(
            String role
    ) {
        return Mission.toModelList(missionRepository.findAllByRole(role));
    }

    public Mission getMissionById(Long missionId) throws NotFoundException {
        Optional<MissionEntity> optionalMission = missionRepository.findById(missionId);
        if (optionalMission.isEmpty()) {
            throw new NotFoundException("Mission with such id wasn't found");
        }
        return Mission.toModel(optionalMission.get());
    }

    public Mission createMission(
            MissionEntity missionBody,
            Long userId
    ) throws MissingCredentialsException, BadRequestException, UserAlreadyExistException, NotFoundException {
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new NotFoundException("User with such id doesn't exist");
        }
        UserEntity existingUser = user.get();

        if(existingUser.getRole().equals("detective") || existingUser.getRole().equals("guilty")){
            if (
                    missionBody.getDescription() == null || missionBody.getDescription().isEmpty() ||
                            missionBody.getTitle() == null || missionBody.getTitle().isEmpty() ||
                            missionBody.getRole().trim().isEmpty()
            ) {
                throw new MissingCredentialsException("Description, title and role(detective, guilty) are required");
            }

        }else if(existingUser.getRole().equals("admin")){
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

        if(!existingUser.getRole().equals("admin")){
            if(!missionBody.getRole().equals(existingUser.getRole())){
                throw new BadRequestException("Your role should be of a type which you are trying to assign the mission!");
            }
        }

        missionBody.setUserId(userId);
        missionBody.setUser(existingUser);
        missionRepository.saveAndFlush(missionBody);
        if(missionBody.getRole().equals("guilty")){
            MissionGuiltyEntity missionGuilty = new MissionGuiltyEntity();
            missionGuilty.setMissionId(missionBody.getId());
            missionGuilty.setMission(missionBody);
            missionGuiltyRepository.save(missionGuilty);
        }

        return Mission.toModel(missionBody);
    }

    public Mission updateMission(
            MissionEntity missionBody,
            Long missionId,
            Long userId
    ) throws NotFoundException, BadRequestException, UserAlreadyExistException, MissingCredentialsException {
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new NotFoundException("User with such id doesn't exist");
        }
        UserEntity existingUser = user.get();

        Optional<MissionEntity> missionOptional = missionRepository.findById(missionId);
        if (missionOptional.isEmpty()){
            throw new NotFoundException("Mission with such id doesn't exist " + missionId);
        }


        MissionEntity existingMission = missionOptional.get();

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

        if(missionBody.getUserId() != null){
            existingMission.setUserId(userId);
        }

        if(missionBody.getTitle() != null){
            if (missionRepository.findByTitle(missionBody.getTitle()) != null){
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

        if(missionBody.getRole() != null){
            existingMission.setRole(missionBody.getRole());
            if(missionBody.getRole().equals("guilty")){
                MissionGuiltyEntity missionGuilty = new MissionGuiltyEntity();
                missionGuilty.setMissionId(existingMission.getId());
                missionGuilty.setMission(existingMission);
                missionGuiltyRepository.save(missionGuilty);
            }
        }
        missionRepository.save(existingMission);
        return Mission.toModel(existingMission);
    }

    public void deleteMission(Long missionId) throws NotFoundException {
        Optional<MissionEntity> missionOptional = missionRepository.findById(missionId);
        if (missionOptional.isEmpty()) {
            throw new NotFoundException("Mission with such id doesn't exist");
        }

        MissionGuiltyEntity missionGuilt = missionGuiltyRepository.findByMissionId(missionId);
        missionGuiltyRepository.delete(missionGuilt);

        missionRepository.deleteById(missionId);
    }
}
