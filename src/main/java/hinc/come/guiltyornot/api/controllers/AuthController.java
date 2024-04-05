package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.domains.UserRoles;
import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
import hinc.come.guiltyornot.api.models.User;
import hinc.come.guiltyornot.api.services.AuthService;
import hinc.come.guiltyornot.api.store.entities.DetectiveEntity;
import hinc.come.guiltyornot.api.store.entities.GuiltyEntity;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
import hinc.come.guiltyornot.api.store.repositories.DetectiveRepository;
import hinc.come.guiltyornot.api.store.repositories.GuiltyRepository;
import hinc.come.guiltyornot.api.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
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
@RequestMapping("/api/v1/auth")
@Transactional
public class AuthController {
    @Autowired
    DetectiveRepository detectiveRepository;
    @Autowired
    GuiltyRepository guiltyRepository;

    @Autowired
    AuthService authService;
    public static final String SIGN_UP = "/registration";


    @PostMapping(SIGN_UP)
    public ResponseEntity<User> registration(@RequestBody UserEntity user) throws UserAlreadyExistException, BadRequestException, MissingCredentialsException {
        try {
            UserEntity createdUser = authService.registration(user);
            UserRoles.valueOf(user.getRole().toUpperCase());

            DetectiveEntity detective = new DetectiveEntity();
            detective.setUser(createdUser);
            detectiveRepository.save(detective);

            GuiltyEntity guilty = new GuiltyEntity();
            guilty.setUser(createdUser);
            guiltyRepository.save(guilty);
            return ResponseEntity.ok().body(User.toModel(createdUser));
        } catch (UserAlreadyExistException e) {
            throw new UserAlreadyExistException("Something went wrong: " + e.getMessage());
        } catch (MissingCredentialsException e) {
            throw new MissingCredentialsException("Something went wrong: " + e.getMessage());
        }  catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role value. Role may be detective, guilty or not_guilty");
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<User> login(@RequestBody UserEntity user) throws BadRequestException, NotFoundException {
        try {
            UserEntity currentUser = authService.login(user);
            return ResponseEntity.ok().body(User.toModel(currentUser));
        } catch (NotFoundException e) {
            throw new NotFoundException("Something went wrong: " + e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }
}
