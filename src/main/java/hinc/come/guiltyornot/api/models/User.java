package hinc.come.guiltyornot.api.models;

import hinc.come.guiltyornot.api.store.entities.AnswerEntity;
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
public class User {
    Long id;
    String username;
    String role;

    public static User toModel(UserEntity entity) {
        User model = new User();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setRole(entity.getRole());
        return model;
    }
    public static List<User> toModelList(List<UserEntity> entities) {
        return entities.stream().map(User::toModel).collect(Collectors.toList());
    }
}
