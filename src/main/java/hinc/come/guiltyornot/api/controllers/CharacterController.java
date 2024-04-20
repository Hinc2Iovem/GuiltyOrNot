package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.services.CharacterService;
import hinc.come.guiltyornot.api.store.entities.CharacterEntity;
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
@RequestMapping("/api/v1/characters")
public class CharacterController {
    @Autowired
    CharacterService characterService;

    @PostMapping
    public ResponseEntity<CharacterEntity> createCharacter(
            @RequestBody CharacterEntity characterBody
    ) throws BadRequestException {
        try {

            CharacterEntity character = characterService.createCharacter(characterBody);
            return ResponseEntity.ok().body(character);
        }catch(Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }
}
