package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.domains.UserRoles;
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
    public static final String UPDATE_USER_LOGIN = "/{userId}/login";
    public static final String UPDATE_USER_PASSWORD = "/{userId}/password";
    public static final String UPDATE_USER_ROLE = "/{userId}/roles/{role}";
    public static final String UPDATE_USER_STATES = "/{userId}/missions/{missionId}/isFinished/{isFinished}";

    @CrossOrigin(origins = "http://localhost:5173")
    @PatchMapping(UPDATE_USER_LOGIN)
    public ResponseEntity<User> updateUserLogin(
            @PathVariable(name = "userId") Long userId,
            @RequestBody UserEntity user
            ) throws NotFoundException, BadRequestException {
        try {
            UserEntity updatedUser = userService.updateUserLogin(userId, user);
            return ResponseEntity.ok().body(User.toModel(updatedUser));
        } catch (NotFoundException e) {
            throw new NotFoundException("Something went wrong: " + e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PatchMapping(UPDATE_USER_PASSWORD)
    public ResponseEntity<User> updateUserPassword(
            @PathVariable(name = "userId") Long userId,
            @RequestBody UserEntity user
    ) throws NotFoundException, BadRequestException {
        try {
            UserEntity updatedUser = userService.updateUserPassword(userId, user);
            return ResponseEntity.ok().body(User.toModel(updatedUser));
        } catch (NotFoundException e) {
            throw new NotFoundException("Something went wrong: " + e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PatchMapping(UPDATE_USER_ROLE)
    public ResponseEntity<User> updateUserRole(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "role") String role
            ) throws NotFoundException, BadRequestException {
        try {
            UserRoles.valueOf(role.toUpperCase());
            UserEntity updatedUser = userService.updateUserRole(userId, role);
            return ResponseEntity.ok().body(User.toModel(updatedUser));
        } catch (NotFoundException e) {
            throw new NotFoundException("Something went wrong: " + e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
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

    @CrossOrigin(origins = "http://localhost:5173")
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
