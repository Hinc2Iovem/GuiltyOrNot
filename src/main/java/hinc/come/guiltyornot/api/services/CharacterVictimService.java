package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.models.CharacterGuilty;
import hinc.come.guiltyornot.api.models.CharacterVictim;
import hinc.come.guiltyornot.api.store.entities.CharacterGuiltyEntity;
import hinc.come.guiltyornot.api.store.entities.CharacterVictimEntity;
import hinc.come.guiltyornot.api.store.entities.MissionDetectiveEntity;
import hinc.come.guiltyornot.api.store.repositories.CharacterGuiltyRepository;
import hinc.come.guiltyornot.api.store.repositories.CharacterVictimRepository;
import hinc.come.guiltyornot.api.store.repositories.MissionDetectiveRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterVictimService {
    @Autowired
    CharacterVictimRepository characterVictimRepository;

    @Autowired
    MissionDetectiveRepository missionDetectiveRepository;

    public CharacterVictim getVictim(
            Long missionId
    ) throws NotFoundException {
        Optional<MissionDetectiveEntity> missionDetectiveOptional = missionDetectiveRepository.findById(missionId);
        if(missionDetectiveOptional.isEmpty()){
            throw new NotFoundException("Mission with such id doesn't exist");
        }
        CharacterVictimEntity character = characterVictimRepository.findByMissionId(missionId);
        return CharacterVictim.toModel(character);
    }
}
