package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.models.User;
import hinc.come.guiltyornot.api.services.UserService;
import hinc.come.guiltyornot.store.entities.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    public static final String UPDATE_DELETE_USER = "/{userId}";

    @PatchMapping(UPDATE_DELETE_USER)
    public ResponseEntity updateUser(
            @PathVariable(name = "userId") Long userId,
            @RequestBody UserEntity user
            ) {
        try {
            UserEntity updatedUser = userService.updateUser(userId, user);
            return ResponseEntity.ok().body(User.toModel(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @DeleteMapping(UPDATE_DELETE_USER)
    public ResponseEntity deleteUser(
            @PathVariable(name = "userId") Long userId
    ) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().body("User with id: " + userId + " was deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }
}
