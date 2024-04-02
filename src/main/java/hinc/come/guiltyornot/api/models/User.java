package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    Long id;
    String username;
    String role;
    Integer exp;
    Integer money;

//    Do I need to add succeededMissions and failedMissions here?
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
