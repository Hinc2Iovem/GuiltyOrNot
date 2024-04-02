package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.domains.UserRoles;
import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
import hinc.come.guiltyornot.api.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public UserEntity registration(UserEntity user) throws UserAlreadyExistException, MissingCredentialsException {
        if (userRepository.findByUsername(user.getUsername()) != null){
            throw new UserAlreadyExistException("User with such username already exist");
        }

        if (user.getUsername() == null || user.getUsername().isEmpty() ||
            user.getPassword() == null || user.getPassword().isEmpty() ||
            user.getRole() == null || user.getRole().isEmpty()) {
            throw new MissingCredentialsException("All fields required: username, password, role");
        }



        return userRepository.save(user);
    }

    public UserEntity login(UserEntity user) throws NotFoundException, MissingCredentialsException, BadRequestException {
        if (userRepository.findByUsername(user.getUsername()) == null){
            throw new NotFoundException("Wrong credentials");
        }

        if (user.getUsername() == null || user.getUsername().isEmpty() ||
            user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new MissingCredentialsException("All fields required: username, password");
        }

        UserEntity currentUser = userRepository.findByUsername(user.getUsername());

        if(!currentUser.getPassword().equals(user.getPassword())){
            throw new BadRequestException("Wrong credentials");
        }
        return currentUser;
    }
}
