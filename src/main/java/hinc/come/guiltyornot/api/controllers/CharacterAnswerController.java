package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.models.CharacterAnswer;
import hinc.come.guiltyornot.api.services.CharacterAnswerService;
import hinc.come.guiltyornot.api.services.CharacterQuestionService;
import hinc.come.guiltyornot.api.store.entities.CharacterAnswerEntity;
import hinc.come.guiltyornot.api.store.entities.CharacterQuestionEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/characters/{characterId}/questions/{questionId}/answers")
public class CharacterAnswerController {
    @Autowired
    CharacterAnswerService characterAnswerService;

    private static final String ANSWER_BY_CHARACTER_ID_SINGLE = "/{answerId}";

    @GetMapping
    public ResponseEntity<List<CharacterAnswer>> getCharacterAnswers(
            @PathVariable Long questionId,
            @PathVariable Long characterId
    ) throws BadRequestException {
        try {
            List<CharacterAnswer> characterQuestions = characterAnswerService.getCharacterAnswers(questionId, characterId);
            return ResponseEntity.ok().body(characterQuestions);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<CharacterAnswer> createCharacterAnswer(
            @PathVariable Long questionId,
            @RequestBody CharacterAnswerEntity characterABody,
            @PathVariable Long characterId
    ) throws BadRequestException {
        try {
            CharacterAnswer characterQuestion = characterAnswerService.createCharacterAnswer(questionId, characterABody, characterId);
            return ResponseEntity.ok().body(characterQuestion);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @Transactional
    @DeleteMapping(ANSWER_BY_CHARACTER_ID_SINGLE)
    public ResponseEntity<String> deleteCharacterAnswer(
            @PathVariable Long answerId,
            @PathVariable Long questionId,
            @PathVariable Long characterId
    ) throws BadRequestException {
        try {
            String response = characterAnswerService.deleteCharacterAnswer(answerId, questionId, characterId);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }
}
