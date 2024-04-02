package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
import hinc.come.guiltyornot.api.models.User;
import hinc.come.guiltyornot.api.services.AuthService;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    AuthService authService;
    public static final String SIGN_UP = "/registration";

    @PostMapping(SIGN_UP)
    public ResponseEntity<User> registration(@RequestBody UserEntity user) throws UserAlreadyExistException, BadRequestException, MissingCredentialsException {
        try {
            UserEntity createdUser = authService.registration(user);
            return ResponseEntity.ok().body(User.toModel(createdUser));
        } catch (UserAlreadyExistException e) {
            throw new UserAlreadyExistException("Something went wrong: " + e.getMessage());
        } catch (MissingCredentialsException e) {
            throw new MissingCredentialsException("Something went wrong: " + e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity login(@RequestBody UserEntity user) {
        try {
            UserEntity currentUser = authService.login(user);
            return ResponseEntity.ok().body(User.toModel(currentUser));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }
}
