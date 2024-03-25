package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.MissingCredentials;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
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

    public UserEntity registration(UserEntity user) throws UserAlreadyExistException, MissingCredentials {
        if (userRepository.findByUsername(user.getUsername()) != null){
            throw new UserAlreadyExistException("User with such username already exist");
        }

        if (user.getUsername() == null || user.getUsername().isEmpty() ||
            user.getPassword() == null || user.getPassword().isEmpty() ||
            user.getRole() == null || user.getRole().isEmpty()) {
            throw new MissingCredentials("All fields required: username, password, role");
        }
        return userRepository.save(user);
    }
}
