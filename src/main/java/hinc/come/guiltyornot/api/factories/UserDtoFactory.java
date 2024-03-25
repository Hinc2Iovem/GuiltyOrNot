package hinc.come.guiltyornot.api.factories;

import hinc.come.guiltyornot.api.dto.UserDto;
import hinc.come.guiltyornot.store.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoFactory {
    public UserDto makeUserDto(UserEntity entity) {

        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .exp(entity.getExp())
                .money(entity.getMoney())
                .role(entity.getRole())
                .build();
    }
}
