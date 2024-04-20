package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.services.CharacterService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/characters")
public class CharacterController {
    @Autowired
    CharacterService characterService;
}
