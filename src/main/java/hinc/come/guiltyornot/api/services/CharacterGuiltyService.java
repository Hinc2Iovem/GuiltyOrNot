package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.models.CharacterGuilty;
import hinc.come.guiltyornot.api.store.entities.CharacterGuiltyEntity;
import hinc.come.guiltyornot.api.store.entities.MissionDetectiveEntity;
import hinc.come.guiltyornot.api.store.repositories.CharacterGuiltyRepository;
import hinc.come.guiltyornot.api.store.repositories.MissionDetectiveRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterGuiltyService {
    @Autowired
    CharacterGuiltyRepository characterGuiltyRepository;
    @Autowired
    MissionDetectiveRepository missionDetectiveRepository;

    public CharacterGuilty getGuilty(
            Long missionId
    ) throws NotFoundException {
        Optional<MissionDetectiveEntity> missionDetectiveOptional = missionDetectiveRepository.findById(missionId);
        if(missionDetectiveOptional.isEmpty()){
            throw new NotFoundException("Mission with such id doesn't exist");
        }
        CharacterGuiltyEntity character = characterGuiltyRepository.findByMissionId(missionId);
        return CharacterGuilty.toModel(character);
    }
}
