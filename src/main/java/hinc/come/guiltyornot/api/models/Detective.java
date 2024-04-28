package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.DetectiveEntity;
import hinc.come.guiltyornot.api.store.entities.FinishedMissionDetectiveEntity;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Detective {
    Long id;

    Integer exp;

    Integer money;

    String imgUrl;

    Long userId;

    public static Detective toModel(DetectiveEntity entity) {
        Detective model = new Detective();
        model.setId(entity.getId());
        model.setExp(entity.getExp());
        model.setMoney(entity.getMoney());
        model.setImgUrl(entity.getImgUrl());
        model.setUserId(entity.getUserId());
        return model;
    }
    public static List<Detective> toModelList(List<DetectiveEntity> entities) {
        return entities.stream().map(Detective::toModel).collect(Collectors.toList());
    }
}
