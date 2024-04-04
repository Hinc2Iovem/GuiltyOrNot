package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.models.User;
import hinc.come.guiltyornot.api.services.UserService;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
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

    public static final String UPDATE_DELETE_USER = "/{userId}";
    public static final String UPDATE_USER_States = "/{userId}/missions/{missionId}";

    @PatchMapping(UPDATE_DELETE_USER)
    public ResponseEntity<User> updateUserLogin(
            @PathVariable(name = "userId") Long userId,
            @RequestBody String userName
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

    @PatchMapping(UPDATE_DELETE_USER)
    public ResponseEntity<User> updateUserPassword(
            @PathVariable(name = "userId") Long userId,
            @RequestBody String userPassword
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

    @PatchMapping(UPDATE_USER_States)
    public ResponseEntity<User> updateUserStates(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "missionId") Long missionId,
            @RequestBody Boolean isFinished
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

    @DeleteMapping(UPDATE_DELETE_USER)
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
