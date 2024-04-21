package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.services.CharacterAnswerService;
import hinc.come.guiltyornot.api.services.CharacterQuestionService;
import hinc.come.guiltyornot.api.store.entities.CharacterAnswerEntity;
import hinc.come.guiltyornot.api.store.entities.CharacterQuestionEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/characters/questions")
public class CharacterAnswerController {
    @Autowired
    CharacterAnswerService characterAnswerService;

    private static final String ANSWER_BY_CHARACTER_ID_SINGLE = "/{questionId}/answers/{answerId}";
    private static final String ANSWER_BY_CHARACTER_ID = "/{questionId}/answers";

    @GetMapping(ANSWER_BY_CHARACTER_ID)
    public ResponseEntity<Stream<CharacterAnswerEntity>> getCharacterAnswers(
            @PathVariable Long questionId
    ) throws BadRequestException {
        try {
            Stream<CharacterAnswerEntity> characterQuestions = characterAnswerService.getCharacterAnswers(questionId);
            return ResponseEntity.ok().body(characterQuestions);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PostMapping(ANSWER_BY_CHARACTER_ID)
    public ResponseEntity<CharacterAnswerEntity> createCharacterAnswer(
            @PathVariable Long questionId,
            CharacterAnswerEntity characterABody
    ) throws BadRequestException {
        try {
            CharacterAnswerEntity characterQuestion = characterAnswerService.createCharacterAnswer(questionId, characterABody);
            return ResponseEntity.ok().body(characterQuestion);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PostMapping(ANSWER_BY_CHARACTER_ID_SINGLE)
    public ResponseEntity<String> deleteCharacterAnswer(
            @PathVariable Long answerId,
            @PathVariable Long questionId
    ) throws BadRequestException {
        try {
            String response = characterAnswerService.deleteCharacterAnswer(answerId, questionId);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }
}
