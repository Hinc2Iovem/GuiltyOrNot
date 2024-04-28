package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.models.DetectiveImages;
import hinc.come.guiltyornot.api.models.MissionDetectiveCharacters;
import hinc.come.guiltyornot.api.store.entities.DetectiveEntity;
import hinc.come.guiltyornot.api.store.entities.DetectiveImagesEntity;
import hinc.come.guiltyornot.api.store.entities.MissionDetectiveCharactersEntity;
import hinc.come.guiltyornot.api.store.entities.MissionDetectiveEntity;
import hinc.come.guiltyornot.api.store.repositories.DetectiveImagesRepository;
import hinc.come.guiltyornot.api.store.repositories.DetectiveRepository;
import hinc.come.guiltyornot.api.store.repositories.MissionDetectiveCharactersRepository;
import hinc.come.guiltyornot.api.store.repositories.MissionDetectiveRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionDetectiveCharactersService {
    @Autowired
    MissionDetectiveCharactersRepository missionDetectiveCharactersRepository;
    @Autowired
    MissionDetectiveRepository missionDetectiveRepository;
    public List<MissionDetectiveCharacters> getAllCharacterIds(
            Long missionId
    ) throws NotFoundException {
        Optional<MissionDetectiveEntity> missionDetectiveOptional = missionDetectiveRepository.findById(missionId);
        if(missionDetectiveOptional.isEmpty()){
            throw new NotFoundException("Detective with such id doesn't exist");
        }
        List<MissionDetectiveCharactersEntity> characterIds = missionDetectiveCharactersRepository.findByMissionDetectiveId(missionId);

        return MissionDetectiveCharacters.toModelList(characterIds);
    }
}
