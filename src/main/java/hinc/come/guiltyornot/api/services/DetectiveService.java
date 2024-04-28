package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.models.Detective;
import hinc.come.guiltyornot.api.store.entities.DetectiveEntity;
import hinc.come.guiltyornot.api.store.entities.DetectiveImagesEntity;
import hinc.come.guiltyornot.api.store.repositories.DetectiveImagesRepository;
import hinc.come.guiltyornot.api.store.repositories.DetectiveRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetectiveService {
    @Autowired
    DetectiveRepository detectiveRepository;
    @Autowired
    DetectiveImagesRepository detectiveImagesRepository;
    public Detective uploadImgUrl(
            String imgUrl,
            Long detectiveId
    ) throws NotFoundException {
        Optional<DetectiveEntity> detectiveOptional = detectiveRepository.findById(detectiveId);
        if(detectiveOptional.isEmpty()){
            throw new NotFoundException("Detective with such id doesn't exist");
        }
        DetectiveEntity detective = detectiveOptional.get();
        imgUrl = imgUrl.trim();
        detective.setImgUrl(imgUrl);
        DetectiveImagesEntity detectiveImages = new DetectiveImagesEntity();
        detectiveImages.setDetective(detective);
        detectiveImages.setDetectiveId(detectiveId);
        detectiveImages.setImgUrl(imgUrl);
        return Detective.toModel(detectiveRepository.save(detective));
    }
}
