package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.store.entities.UserEntity;
import hinc.come.guiltyornot.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public UserEntity registration(UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()) != null){
            return null;
        }
        return userRepository.save(user);
    }
}
