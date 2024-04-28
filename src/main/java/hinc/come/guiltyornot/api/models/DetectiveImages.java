package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.DetectiveImagesEntity;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetectiveImages {
    Long id;
    Long detectiveId;
    String imgUrl;

    public static DetectiveImages toModel(DetectiveImagesEntity entity) {
        DetectiveImages model = new DetectiveImages();
        model.setId(entity.getId());
        model.setDetectiveId(entity.getDetectiveId());
        model.setImgUrl(entity.getImgUrl());
        return model;
    }
    public static List<DetectiveImages> toModelList(List<DetectiveImagesEntity> entities) {
        return entities.stream().map(DetectiveImages::toModel).collect(Collectors.toList());
    }
}
