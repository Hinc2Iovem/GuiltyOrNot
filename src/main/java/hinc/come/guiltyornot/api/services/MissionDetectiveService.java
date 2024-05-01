package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
import hinc.come.guiltyornot.api.models.Character;
import hinc.come.guiltyornot.api.models.MissionDetective;
import hinc.come.guiltyornot.api.store.entities.*;
import hinc.come.guiltyornot.api.store.repositories.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionDetectiveService {
    @Autowired
    MissionDetectiveRepository missionDetectiveRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DetectiveRepository detectiveRepository;
    @Autowired
    CharacterRepository characterRepository;
    @Autowired
    CharacterVictimRepository characterVictimRepository;
    @Autowired
    CharacterGuiltyRepository characterGuiltyRepository;
    @Autowired
    MissionDetectiveCharactersRepository missionDetectiveCharactersRepository;

    @Transactional(readOnly = true)
    public List<MissionDetective> getMissions() {
        return MissionDetective.toModelList(missionDetectiveRepository.findAllBy());
    }

    public MissionDetective getMissionById(Long missionId) throws NotFoundException {
        Optional<MissionDetectiveEntity> optionalMission = missionDetectiveRepository.findById(missionId);
        if (optionalMission.isEmpty()) {
            throw new NotFoundException("Mission with such id wasn't found");
        }
        return MissionDetective.toModel(optionalMission.get());
    }

    public MissionDetective createMission(
            MissionDetectiveEntity missionBody,
            Long userId
    ) throws MissingCredentialsException, BadRequestException, NotFoundException {
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new NotFoundException("Detective with such id doesn't exist");
        }
        UserEntity existingUser = user.get();
        MissionDetectiveEntity bodyForSending = new MissionDetectiveEntity();

        Optional<DetectiveEntity> detective = detectiveRepository.findById(userId);
        if(detective.isEmpty()){
            throw new NotFoundException("Detective with such id doesn't exist");
        }
        DetectiveEntity existingDetective = detective.get();
        bodyForSending.setDetectiveId(userId);
        bodyForSending.setDetective(existingDetective);

        if(existingUser.getRole().equalsIgnoreCase("detective") || existingUser.getRole().equalsIgnoreCase("admin")){
            if(missionBody.getDescription() == null || missionBody.getDescription().isEmpty() ||
                missionBody.getTitle() == null || missionBody.getTitle().isEmpty() ||
                missionBody.getCharacterIds().isEmpty() || missionBody.getCharacterGuiltyId() == null
                    || missionBody.getImgUrl() == null || missionBody.getImgUrl().trim().isEmpty()
            ) {
                throw new MissingCredentialsException("Description, CharacterGuiltyId, CharacterIds, title and imgUrl are required");
            }
            bodyForSending.setDescription(missionBody.getDescription());
            bodyForSending.setTitle(missionBody.getTitle());
            bodyForSending.setImgUrl(missionBody.getImgUrl());
            bodyForSending.setRole(missionBody.getRole());
            if(existingUser.getRole().equalsIgnoreCase("admin")){
                if(missionBody.getDefeatExp() == 0 || missionBody.getDefeatMoney() == 0 ||
                        missionBody.getRewardExp() == 0 || missionBody.getRewardMoney() == 0){
                    throw new MissingCredentialsException("defeatExp, defeatMoney, rewardExp and rewardMoney are required");
                }
                bodyForSending.setDefeatExp(missionBody.getDefeatExp());
                bodyForSending.setDefeatMoney(missionBody.getDefeatMoney());
                bodyForSending.setRewardExp(missionBody.getRewardExp());
                bodyForSending.setRewardMoney(missionBody.getRewardMoney());
            }
        }

            List<CharacterEntity> characters = characterRepository.findAllById(missionBody.getCharacterIds());
            for(CharacterEntity c : characters){
               if(c.getName() == null || c.getName().trim().isEmpty()) {
                    throw new NotFoundException("Character with id " + c.getId().toString() + " doesn't exist");
               }
        }
            List<Long> characterIds = new ArrayList<>();
            for (CharacterEntity characterEntity : characters) {
                characterIds.add(characterEntity.getId());
            }
            bodyForSending.setCharacterIds(characterIds);
            bodyForSending.setCharacters(characters);



        int levelOfDifficulty;
        if(missionBody.getLevelOfDifficulty() != null) {
            levelOfDifficulty = missionBody.getLevelOfDifficulty();
            if(levelOfDifficulty < 1 || levelOfDifficulty > 5){
                throw new BadRequestException("Level of difficulty can not be less than 1 or more than 5!");
            }
            bodyForSending.setLevelOfDifficulty(levelOfDifficulty);
        }


        if(missionBody.getWithVictim()){
            Optional<CharacterEntity> currentCharacterVictimOptional = characterRepository.findById(missionBody.getCharacterVictimId());
            if(currentCharacterVictimOptional.isEmpty()){
                throw new NotFoundException("Victim with such id doesn't exist");
            }
            CharacterEntity currentCharacterVictim = currentCharacterVictimOptional.get();
            CharacterVictimEntity victim = new CharacterVictimEntity();
            victim.setCharacterEntityId(missionBody.getCharacterVictimId());
            victim.setCharacter(currentCharacterVictim);
            characterVictimRepository.saveAndFlush(victim);
            bodyForSending.setCharacterVictimId(missionBody.getCharacterVictimId());
            bodyForSending.setCharacterVictim(victim);
            bodyForSending.setWithVictim(true);
        }

        if(missionBody.getWithVictim() == null){
            bodyForSending.setWithVictim(false);
        }

        Optional<CharacterEntity> currentCharacterGuiltyOptional = characterRepository.findById(missionBody.getCharacterGuiltyId());
        if(currentCharacterGuiltyOptional.isEmpty()){
            throw new NotFoundException("Guilty with such id doesn't exist");
        }
        CharacterEntity currentCharacterGuilty = currentCharacterGuiltyOptional.get();
        CharacterGuiltyEntity guilty = new CharacterGuiltyEntity();
        guilty.setCharacterEntityId(missionBody.getCharacterGuiltyId());
        guilty.setCharacter(currentCharacterGuilty);
        characterGuiltyRepository.saveAndFlush(guilty);

        bodyForSending.setCharacterGuilty(guilty);
        bodyForSending.setCharacterGuiltyId(missionBody.getCharacterGuiltyId());

        missionDetectiveRepository.saveAndFlush(bodyForSending);

        MissionDetectiveCharactersEntity missionDetectiveCharacters = new MissionDetectiveCharactersEntity();
        missionDetectiveCharacters.setMissionDetective(bodyForSending);
        missionDetectiveCharacters.setCharacterIds(characterIds.toString());
        missionDetectiveCharacters.setMissionDetectiveId(bodyForSending.getId());
        missionDetectiveCharactersRepository.save(missionDetectiveCharacters);

        for(CharacterEntity c : characters){
            c.setMissionDetectiveId(missionBody.getId());
            c.setMissionDetective(bodyForSending);
            characterRepository.save(c);
        }
        return MissionDetective.toModel(bodyForSending);
    }

    public MissionDetective updateMission(
            MissionDetectiveEntity missionBody,
            Long missionId,
            Long userId
    ) throws NotFoundException, BadRequestException, UserAlreadyExistException {
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

        if(existingUser.getRole().equalsIgnoreCase("admin")){
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



        if(missionBody.getDetectiveId() != null){
            Optional<DetectiveEntity> detective = detectiveRepository.findById(userId);
            if(detective.isEmpty()){
                throw new NotFoundException("Detective with such id doesn't exist");
            }
            DetectiveEntity existingDetective = detective.get();
            existingMission.setDetectiveId(userId);
            existingMission.setDetective(existingDetective);
        }

        if(missionBody.getWithVictim()){
            existingMission.setWithVictim(true);
            if(missionBody.getCharacterVictimId() != null){
                Optional<CharacterVictimEntity> victimOptional = characterVictimRepository.findById(missionBody.getCharacterVictimId());
                if(victimOptional.isEmpty()){
                    throw new NotFoundException("Victim with such id doesn't exist");
                }
                CharacterVictimEntity victim = victimOptional.get();
                existingMission.setCharacterVictim(victim);
                existingMission.setCharacterVictimId(missionBody.getCharacterVictimId());
            }
        }

        if(missionBody.getCharacterGuiltyId() != null) {
            Optional<CharacterGuiltyEntity> guiltyOptional = characterGuiltyRepository.findById(missionBody.getCharacterGuiltyId());
            if(guiltyOptional.isEmpty()){
                throw new NotFoundException("Guilty with such id doesn't exist");
            }
            CharacterGuiltyEntity guilty = guiltyOptional.get();
            existingMission.setCharacterGuilty(guilty);
            existingMission.setCharacterGuiltyId(missionBody.getCharacterGuiltyId());
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


        return MissionDetective.toModel(missionDetectiveRepository.save(existingMission));
    }

    public void deleteMission(Long missionId) throws NotFoundException {
        Optional<MissionDetectiveEntity> missionOptional = missionDetectiveRepository.findById(missionId);
        if (missionOptional.isEmpty()) {
            throw new NotFoundException("Mission with such id doesn't exist");
        }

        missionDetectiveRepository.deleteById(missionId);
    }


}
