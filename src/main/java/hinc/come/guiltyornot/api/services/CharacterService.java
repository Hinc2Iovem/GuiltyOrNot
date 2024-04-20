package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.store.entities.CharacterEntity;
import hinc.come.guiltyornot.api.store.entities.MissionEntity;
import hinc.come.guiltyornot.api.store.repositories.CharacterRepository;
import hinc.come.guiltyornot.api.store.repositories.MissionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterService {
    @Autowired
    CharacterRepository characterRepository;
    @Autowired
    MissionRepository missionRepository;

    public CharacterEntity createCharacter(CharacterEntity characterBody) throws MissingCredentialsException, BadRequestException {
        if(characterBody.getDescription() == null || characterBody.getDescription().trim().isEmpty()
                || characterBody.getName() == null || characterBody.getName().trim().isEmpty()
                || characterBody.getHairColor() == null || characterBody.getHairColor().trim().isEmpty()
                || characterBody.getGender() == null || characterBody.getGender().trim().isEmpty()
                || characterBody.getAge() == null || characterBody.getAge() == 0
                || characterBody.getFeature() == null || characterBody.getFeature().trim().isEmpty()
        ) {
            throw new MissingCredentialsException("Description, name, hairColor, gender, age and feature are required!");
        }

        if(characterBody.getLevelOfDifficulty() <= 0 || characterBody.getLevelOfDifficulty() > 5){
            throw new BadRequestException("level of difficulty can not be less than 0 or more than 5");
        }

        return characterRepository.save(characterBody);
    }

    public String assignCharactersToMission(
            Long missionId,
            List<Long> charactersId
    ) throws NotFoundException {
        Optional<MissionEntity> mission = missionRepository.findById(missionId);
        if(mission.isEmpty()){
            throw new NotFoundException("Mission with such id doesn't exist");
        }

        List<CharacterEntity> characters = characterRepository.findAllById(charactersId);
        for(CharacterEntity c : characters){
            if(c.getName() == null || c.getName().trim().isEmpty()) {
                throw new NotFoundException("Character with id \"" + c.getId().toString() + "\" doesn't exist");
            }
        }
        MissionEntity existingMission = mission.get();

    }
}
