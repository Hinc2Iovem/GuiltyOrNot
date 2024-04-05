package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.models.Question;
import hinc.come.guiltyornot.api.services.QuestionService;
import hinc.come.guiltyornot.api.store.entities.QuestionEntity;
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
@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    private static final String SINGLE_QUESTION = "/{questionId}";
    private static final String GET_QUESTIONS_BY_MISSION_ID = "/missions/{missionId}";

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<Question>> getQuestions() throws BadRequestException {
        try {
            List<QuestionEntity> questions = questionService.getQuestions();
            return ResponseEntity.ok().body(Question.toModelList(questions));
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong " + e.getMessage());
        }
    }

    @GetMapping(GET_QUESTIONS_BY_MISSION_ID)
    @Transactional(readOnly = true)
    public ResponseEntity<List<Question>> getQuestionsByMissionId(
            @PathVariable Long missionId
    ) throws BadRequestException {
        try {
            List<QuestionEntity> questions = questionService.getQuestionsByMissionId(missionId);
            return ResponseEntity.ok().body(Question.toModelList(questions));
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong " + e.getMessage());
        }
    }

    @GetMapping(SINGLE_QUESTION)
    public ResponseEntity<Question> getQuestionById(@PathVariable(name = "questionId") Long questionId) throws BadRequestException {
        try {
            QuestionEntity question = questionService.getQuestionById(questionId);
            return ResponseEntity.ok().body(Question.toModel(question));
        } catch (Exception e){
            throw new BadRequestException("Something went wrong " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody QuestionEntity questionBody) throws BadRequestException {
        try {
            QuestionEntity question = questionService.createQuestion(questionBody);
            return ResponseEntity.ok().body(Question.toModel(question));
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong " + e.getMessage());
        }
    }

    @PatchMapping(SINGLE_QUESTION)
    public ResponseEntity<Question> updateQuestion(
            @RequestBody QuestionEntity questionBody,
            @PathVariable(name = "questionId") Long questionId
    ) throws BadRequestException {
        try {
            QuestionEntity question = questionService.updateQuestion(questionBody, questionId);
            return ResponseEntity.ok().body(Question.toModel(question));
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong " + e.getMessage());
        }
    }

    @DeleteMapping(SINGLE_QUESTION)
    public ResponseEntity<String> deleteQuestion(
            @PathVariable(name = "questionId") Long questionId
    ) {
        try {
            questionService.deleteQuestion(questionId);
            return ResponseEntity.ok("Question with id: " + questionId + " was deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong " + e.getMessage());
        }
    }
}
