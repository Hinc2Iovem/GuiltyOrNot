package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.models.User;
import hinc.come.guiltyornot.api.services.UserService;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
import hinc.come.guiltyornot.api.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    public static final String DELETE_USER = "/{userId}";
    public static final String UPDATE_USER_LOGIN = "/{userId}/login/{login}";
    public static final String UPDATE_USER_PASSWORD = "/{userId}/password/{password}";

    public static final String UPDATE_USER_STATES = "/{userId}/missions/{missionId}/isFinished/{isFinished}";

    @PatchMapping(UPDATE_USER_LOGIN)
    public ResponseEntity<User> updateUserLogin(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "login") String userName
            ) throws NotFoundException, BadRequestException {
        try {
            UserEntity updatedUser = userService.updateUserLogin(userId, userName);
            return ResponseEntity.ok().body(User.toModel(updatedUser));
        } catch (NotFoundException e) {
            throw new NotFoundException("Something went wrong: " + e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PatchMapping(UPDATE_USER_PASSWORD)
    public ResponseEntity<User> updateUserPassword(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "password") String userPassword
    ) throws NotFoundException, BadRequestException {
        try {
            UserEntity updatedUser = userService.updateUserPassword(userId, userPassword);
            return ResponseEntity.ok().body(User.toModel(updatedUser));
        } catch (NotFoundException e) {
            throw new NotFoundException("Something went wrong: " + e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PatchMapping(UPDATE_USER_STATES)
    public ResponseEntity<User> updateUserStates(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "missionId") Long missionId,
            @PathVariable(name = "isFinished") Boolean isFinished
    ) throws NotFoundException, BadRequestException {
        try {
            UserEntity updatedUser = userService.updateUserStates(userId, missionId, isFinished);
            return ResponseEntity.ok().body(User.toModel(updatedUser));
        } catch (NotFoundException e) {
            throw new NotFoundException("Something went wrong: " + e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @DeleteMapping(DELETE_USER)
    public ResponseEntity deleteUser(
            @PathVariable(name = "userId") Long userId
    ) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().body("User with id: " + userId + " was deleted");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }
}
