package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentials;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
import hinc.come.guiltyornot.api.models.User;
import hinc.come.guiltyornot.store.entities.UserEntity;
import hinc.come.guiltyornot.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserEntity updateUser(
            Long userId,
            @RequestBody UserEntity user
    ) throws NotFoundException {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new NotFoundException("User with such id doesn't exist");
        }

        UserEntity existingUser = userOptional.get();

        if(user.getUsername() != null){
            existingUser.setUsername(user.getUsername());
        }
        if(user.getPassword() != null){
            existingUser.setPassword(user.getPassword());
        }
        if(user.getExp() != null){
            existingUser.setExp(user.getExp());
        }
        if(user.getMoney() != null){
            existingUser.setMoney(user.getMoney());
        }
        if(user.getRole() != null){
            existingUser.setRole(user.getRole());
        }

        return userRepository.save(existingUser);
    }
    public Long deleteUser(Long userId) throws NotFoundException {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("User with such id doesn't exist");
        }
        userRepository.deleteById(userId);
        return userId;
    }
}
