package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.models.Character;
import hinc.come.guiltyornot.api.services.CharacterService;
import hinc.come.guiltyornot.api.store.entities.CharacterEntity;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/characters")
public class CharacterController {
    @Autowired
    CharacterService characterService;

    public static final String ASSIGN_CHARACTERS_TO_MISSION = "/missions/{missionId}";
    public static final String SINGLE_CHARACTER = "/{characterId}";
    public static final String DETECTIVE_ID = "/detectives/{detectiveId}";
    @PostMapping(DETECTIVE_ID)
    public ResponseEntity<Character> createCharacter(
            @RequestBody CharacterEntity characterBody,
            @PathVariable Long detectiveId
    ) throws BadRequestException {
        try {
            Character character = characterService.createCharacter(characterBody, detectiveId);
            return ResponseEntity.ok().body(character);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @Transactional
    @GetMapping(DETECTIVE_ID)
    public ResponseEntity<List<Character>> getCharactersByDetectiveId(
           @PathVariable Long detectiveId
    ) throws BadRequestException {
        try {
            List<Character> characters = characterService.getCharactersByDetectiveId(detectiveId);
            return ResponseEntity.ok().body(characters);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }
    @Transactional
    @GetMapping(SINGLE_CHARACTER)
    public ResponseEntity<Character> getCharacterById(@PathVariable Long characterId) throws BadRequestException {
        try {
            Character character = characterService.getCharacterById(characterId);
            return ResponseEntity.ok().body(character);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PostMapping(ASSIGN_CHARACTERS_TO_MISSION)
    public ResponseEntity<String> assignCharactersToMission(
            @RequestBody List<Long> charactersId,
            @PathVariable(name = "missionId") Long missionId
    ) throws BadRequestException {
        try {
            String response = characterService.assignCharactersToMission(missionId, charactersId);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PatchMapping(SINGLE_CHARACTER)
    public ResponseEntity<Character> updateCharacter(
            @RequestBody CharacterEntity characterBody,
            @PathVariable(name = "characterId") Long characterId
    ) throws BadRequestException {
        try {
            Character character = characterService.updateCharacter(characterId, characterBody);
            return ResponseEntity.ok().body(character);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @DeleteMapping(SINGLE_CHARACTER)
    public ResponseEntity<String> deleteCharacter(@PathVariable Long characterId) throws BadRequestException {
        try {
            String response = characterService.deleteCharacter(characterId);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }
}
