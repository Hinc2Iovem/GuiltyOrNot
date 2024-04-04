package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.store.entities.DetectiveEntity;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
import hinc.come.guiltyornot.api.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserEntity updateUserLogin(
            Long userId,
            @RequestBody String userName
    ) throws NotFoundException {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new NotFoundException("User with such id doesn't exist");
        }

        UserEntity existingUser = userOptional.get();

        if(!userName.trim().isEmpty()){
            existingUser.setUsername(userName);
        }
//        if(user.getPassword() != null){
//            existingUser.setPassword(user.getPassword());
//        }
//        if(user.getRole() != null){
//            existingUser.setRole(user.getRole());
//        }

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
