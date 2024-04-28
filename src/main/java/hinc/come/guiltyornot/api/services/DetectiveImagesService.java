package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.models.DetectiveImages;
import hinc.come.guiltyornot.api.store.entities.DetectiveEntity;
import hinc.come.guiltyornot.api.store.entities.DetectiveImagesEntity;
import hinc.come.guiltyornot.api.store.repositories.DetectiveImagesRepository;
import hinc.come.guiltyornot.api.store.repositories.DetectiveRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetectiveImagesService {
    @Autowired
    DetectiveImagesRepository detectiveImagesRepository;
    @Autowired
    DetectiveRepository detectiveRepository;
    public List<DetectiveImages> getAllImages(
            Long detectiveId
    ) throws NotFoundException {
        Optional<DetectiveEntity> detectiveOptional = detectiveRepository.findById(detectiveId);
        if(detectiveOptional.isEmpty()){
            throw new NotFoundException("Detective with such id doesn't exist");
        }
        List<DetectiveImagesEntity> images = detectiveImagesRepository.findByDetectiveId(detectiveId);

        return DetectiveImages.toModelList(images);
    }
}
