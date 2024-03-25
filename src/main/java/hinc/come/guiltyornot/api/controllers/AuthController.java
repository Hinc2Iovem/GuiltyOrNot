package hinc.come.guiltyornot.api.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/auth")
public class AuthController {
    public static final String SIGN_UP = "/registration";

//    @PostMapping
//    public
}
