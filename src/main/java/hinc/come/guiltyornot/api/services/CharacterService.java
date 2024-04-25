package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.models.Character;
import hinc.come.guiltyornot.api.store.entities.*;
import hinc.come.guiltyornot.api.store.repositories.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterService {
    @Autowired
    CharacterRepository characterRepository;
    @Autowired
    MissionRepository missionRepository;
    @Autowired
    MissionDetectiveRepository missionDetectiveRepository;
    @Autowired
    DetectiveRepository detectiveRepository;

    public Character createCharacter(
            CharacterEntity characterBody,
            Long detectiveId
    ) throws MissingCredentialsException, BadRequestException, NotFoundException {
        AssignMissionNotNow(characterBody);
        DetectiveEntity detective = DetectiveWithSuchIdDoesntExist(detectiveId);

        if (characterBody.getDescription() == null || characterBody.getDescription().trim().isEmpty()
                || characterBody.getName() == null || characterBody.getName().trim().isEmpty()
                || characterBody.getHairColor() == null || characterBody.getHairColor().trim().isEmpty()
                || characterBody.getGender() == null || characterBody.getGender().trim().isEmpty()
                || characterBody.getAge() == null || characterBody.getAge() == 0
                || characterBody.getFeature() == null || characterBody.getFeature().trim().isEmpty()
        ) {
            throw new MissingCredentialsException("Description, name, hairColor, gender, age, isGuilty, detectiveId and feature are required!");
        }
        AllowableLevelOfDifficulty(characterBody);
        characterBody.setMissionDetective(null);
        characterBody.setMissionDetectiveId(null);
        characterBody.setDetective(detective);
        characterBody.setDetectiveId(detectiveId);
        return Character.toModel(characterRepository.save(characterBody));
    }

    public List<Character> getCharactersByDetectiveId(Long detectiveId) throws NotFoundException {
        DetectiveWithSuchIdDoesntExist(detectiveId);
        return Character.toModelList(characterRepository.findAllByDetectiveId(detectiveId));
    }

    public Character getCharacterById(Long characterId) throws NotFoundException {
        CharacterWithSuchIdDoesntExist(characterId);
        return Character.toModel(characterRepository.findById(characterId).get());
    }

    public String assignCharactersToMission(
            Long missionId,
            List<Long> charactersId
    ) throws NotFoundException, BadRequestException {
        Optional<MissionEntity> mission = missionRepository.findById(missionId);
        if (mission.isEmpty()) {
            throw new NotFoundException("Mission with such id doesn't exist");
        }
        MissionEntity existingMission = mission.get();

        List<CharacterEntity> characters = characterRepository.findAllById(charactersId);
        boolean moreThanOneGuilty = isMoreThanOneGuilty(characters);
        boolean oneGuilty = isThereOneGuilty(characters);
        UserEntity user = existingMission.getUser();
        if (user.getRole().equals("admin")){
            if (moreThanOneGuilty) {
                throw new BadRequestException("There should be only one 1 guilty person!");
            }
            if(!oneGuilty){
                throw new BadRequestException("There should be one guilty person always!");
            }
        }

        MissionDetectiveEntity missionDetective = new MissionDetectiveEntity();
        missionDetective.setMission(existingMission);
        missionDetective.setMissionId(missionId);
        missionDetective.setCharacters(characters);
        missionDetectiveRepository.save(missionDetective);

        return "Detective mission was created with missionId of: \" " + missionId + "\" and with these charactersIds \" " + charactersId;
    }

    public Character updateCharacter(
            Long characterId,
            CharacterEntity characterBody
    ) throws BadRequestException, NotFoundException {
       CharacterEntity existingCharacter = CharacterWithSuchIdDoesntExist(characterId);
        AssignMissionNotNow(characterBody);
        AllowableLevelOfDifficulty(characterBody);

        if(characterBody.getDescription() != null){
            existingCharacter.setDescription(characterBody.getDescription());
        }
        if(characterBody.getDetectiveId() != null){
            existingCharacter.setDetectiveId(characterBody.getDetectiveId());
        }
        if(characterBody.getName() != null){
            existingCharacter.setName(characterBody.getName());
        }
        if(characterBody.getHairColor() != null){
            existingCharacter.setHairColor(characterBody.getHairColor());
        }
        if(characterBody.getGender() != null){
            existingCharacter.setGender(characterBody.getGender());
        }
        if(characterBody.getFeature() != null){
            existingCharacter.setFeature(characterBody.getFeature());
        }
        if(characterBody.getAge() != null){
            existingCharacter.setAge(characterBody.getAge());
        }
        if(characterBody.getLevelOfDifficulty() != null){
            existingCharacter.setLevelOfDifficulty(characterBody.getLevelOfDifficulty());
        }
        if(characterBody.getIsGuilty() != null){
            existingCharacter.setIsGuilty(characterBody.getIsGuilty());
        }

        return Character.toModel(characterRepository.save(existingCharacter));
    }

    public String deleteCharacter(Long characterId) throws NotFoundException {
        CharacterWithSuchIdDoesntExist(characterId);
        characterRepository.deleteById(characterId);
        return "Character with id \" " + characterId + "\" was deleted";
    }

    private void AssignMissionNotNow(CharacterEntity characterBody) throws BadRequestException {
        if(characterBody.getMissionDetective() != null || characterBody.getMissionDetectiveId() != null){
            throw new BadRequestException("You can't assign missionId right now!");
        }
    }

    private void AllowableLevelOfDifficulty(CharacterEntity characterBody) throws BadRequestException {
        if (characterBody.getLevelOfDifficulty() <= 0 || characterBody.getLevelOfDifficulty() > 5) {
            throw new BadRequestException("level of difficulty can not be less than 0 or more than 5");
        }
    }

    private CharacterEntity CharacterWithSuchIdDoesntExist(Long characterId) throws NotFoundException {
        Optional<CharacterEntity> character = characterRepository.findById(characterId);
        if(character.isEmpty()){
            throw new NotFoundException("Character with such id doesn't exist");
        }
        return character.get();
    }

    private DetectiveEntity DetectiveWithSuchIdDoesntExist(Long detectiveId) throws NotFoundException {
        Optional<DetectiveEntity> detective = detectiveRepository.findById(detectiveId);
        if(detective.isEmpty()){
            throw new NotFoundException("Detective with such id doesn't exist");
        }

        return detective.get();
    }

    private static boolean isMoreThanOneGuilty(List<CharacterEntity> characters) throws NotFoundException {
        boolean isGuiltyThere = false;
        boolean moreThanOneGuilty = false;
        for (CharacterEntity c : characters) {
            if (c.getName() == null || c.getName().trim().isEmpty()) {
                throw new NotFoundException("Character with id \"" + c.getId().toString() + "\" doesn't exist");
            }

            if (isGuiltyThere && c.getIsGuilty()) {
                moreThanOneGuilty = true;
            }

            if (c.getIsGuilty()) {
                isGuiltyThere = true;
            }
        }
        return moreThanOneGuilty;
    }

    private static boolean isThereOneGuilty(List<CharacterEntity> characters) throws NotFoundException {
        boolean isGuiltyThere = false;
        for (CharacterEntity c : characters) {
            if (c.getName() == null || c.getName().trim().isEmpty()) {
                throw new NotFoundException("Character with id \"" + c.getId().toString() + "\" doesn't exist");
            }

            if (c.getIsGuilty()) {
                isGuiltyThere = true;
            }
        }
        return isGuiltyThere;
    }
}
