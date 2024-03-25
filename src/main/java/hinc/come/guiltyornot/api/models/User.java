package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.store.entities.SucceededMissionEntity;
import hinc.come.guiltyornot.store.entities.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    Long id;
    String username;
    String role;
    Integer exp;
    Integer money;

    public static User toModel(UserEntity entity) {
        User model = new User();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setRole(entity.getRole());
        model.setExp(entity.getExp());
        model.setMoney(entity.getMoney());
        return model;
    }

}
