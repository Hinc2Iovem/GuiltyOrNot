package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.services.CharacterQuestionService;
import hinc.come.guiltyornot.api.store.entities.CharacterQuestionEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/characters")
public class CharacterQuestionController {
    @Autowired
    CharacterQuestionService characterQuestionService;

    private static final String QUESTION_BY_CHARACTER_ID_SINGLE = "/{characterId}/questions/{questionId}";
    private static final String QUESTION_BY_CHARACTER_ID = "/{characterId}/questions";

    @GetMapping(QUESTION_BY_CHARACTER_ID)
    public ResponseEntity<Stream<CharacterQuestionEntity>> getCharacterQuestions(
            @PathVariable Long characterId
    ) throws BadRequestException {
        try {
            Stream<CharacterQuestionEntity> characterQuestions = characterQuestionService.getCharacterQuestions(characterId);
            return ResponseEntity.ok().body(characterQuestions);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PostMapping(QUESTION_BY_CHARACTER_ID)
    public ResponseEntity<CharacterQuestionEntity> createCharacterQuestion(
            @PathVariable Long characterId,
            CharacterQuestionEntity characterQBody
    ) throws BadRequestException {
        try {
             CharacterQuestionEntity characterQuestion = characterQuestionService.createCharacterQuestion(characterId, characterQBody);
            return ResponseEntity.ok().body(characterQuestion);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PostMapping(QUESTION_BY_CHARACTER_ID_SINGLE)
    public ResponseEntity<String> deleteCharacterQuestion(
            @PathVariable Long characterId,
            @PathVariable Long questionId
    ) throws BadRequestException {
        try {
            String response = characterQuestionService.deleteCharacterQuestion(characterId, questionId);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }
}
