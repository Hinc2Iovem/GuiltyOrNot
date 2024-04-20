package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
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
    public static final String UPDATE_DELETE_CHARACTER = "/{characterId}";
    public static final String GET_BY_ROLE_AND_USER_ID = "/users/{userId}";
    @PostMapping
    public ResponseEntity<CharacterEntity> createCharacter(
            @RequestBody CharacterEntity characterBody
    ) throws BadRequestException {
        try {
            CharacterEntity character = characterService.createCharacter(characterBody);
            return ResponseEntity.ok().body(character);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @Transactional
    @GetMapping(GET_BY_ROLE_AND_USER_ID)
    public ResponseEntity<Stream<CharacterEntity>> getCharactersByRoleAndUserId(
           @PathVariable Long userId
    ) throws BadRequestException {
        try {
            Stream<CharacterEntity> characters = characterService.getCharactersByRoleAndUserId(userId);
            return ResponseEntity.ok().body(characters);
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

    @PatchMapping(UPDATE_DELETE_CHARACTER)
    public ResponseEntity<CharacterEntity> updateCharacter(
            @RequestBody CharacterEntity characterBody,
            @PathVariable(name = "characterId") Long characterId
    ) throws BadRequestException {
        try {
            CharacterEntity character = characterService.updateCharacter(characterId, characterBody);
            return ResponseEntity.ok().body(character);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @DeleteMapping(UPDATE_DELETE_CHARACTER)
    public ResponseEntity<String> deleteCharacter(Long characterId) throws BadRequestException {
        try {
            String response = characterService.deleteCharacter(characterId);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }
}
