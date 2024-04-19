package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.domains.AnswerTypes;
import hinc.come.guiltyornot.api.domains.UserRoles;
import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.models.Answer;
import hinc.come.guiltyornot.api.services.AnswerService;
import hinc.come.guiltyornot.api.store.entities.AnswerEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/answers")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    public static final String SINGLE_ANSWER = "/{answerId}";
    public static final String CREATE_ANSWER = "/questions/{questionId}";
    public static final String GET_ANSWERS_BY_QUESTION_ID = "/questions/{questionId}";

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<Answer>> getAnswers() throws BadRequestException {
        try {
            List<AnswerEntity> answers = answerService.getAnswers();
            return ResponseEntity.ok().body(Answer.toModelList(answers));
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong " + e.getMessage());
        }
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(GET_ANSWERS_BY_QUESTION_ID)
    @Transactional(readOnly = true)
    public ResponseEntity<List<Answer>> getAnswersByQuestionId(
            @PathVariable Long questionId
    ) throws BadRequestException {
        try{
            List<AnswerEntity> answers = answerService.getAnswersByQuestionId(questionId);
            return ResponseEntity.ok().body(Answer.toModelList(answers));
        }catch (Exception e){
            throw new BadRequestException("Something went wrong " + e.getMessage());
        }
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(SINGLE_ANSWER)
    public ResponseEntity<Answer> getAnswerById(@PathVariable(name = "answerId") Long answerId) throws BadRequestException {
        try {
            AnswerEntity answer = answerService.getAnswerById(answerId);
            return ResponseEntity.ok().body(Answer.toModel(answer));
        } catch (Exception e){
            throw new BadRequestException("Something went wrong " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping(CREATE_ANSWER)
    public ResponseEntity<Answer> createAnswer(
            @RequestBody AnswerEntity answerBody,
            @PathVariable(name = "questionId") Long questionId
    ) throws BadRequestException {
        try {
            AnswerEntity answer = answerService.createAnswer(answerBody, questionId);
            AnswerTypes.valueOf(answerBody.getType().toUpperCase());

            return ResponseEntity.ok().body(Answer.toModel(answer));
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PatchMapping(SINGLE_ANSWER)
    public ResponseEntity<Answer> updateAnswer(
            @RequestBody AnswerEntity answerBody,
            @PathVariable(name = "answerId") Long answerId
    ) throws BadRequestException {
        try {
            if(answerBody.getType() != null && !answerBody.getType().isEmpty()){
                AnswerTypes.valueOf(answerBody.getType().toUpperCase());
            }
            AnswerEntity answer = answerService.updateAnswer(answerBody, answerId);
            return ResponseEntity.ok().body(Answer.toModel(answer));
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping(SINGLE_ANSWER)
    public ResponseEntity<String> deleteAnswer(
            @PathVariable(name = "answerId") Long answerId
    ) {
        try {
            answerService.deleteAnswer(answerId);
            return ResponseEntity.ok("Answer with id: " + answerId + " was deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong " + e.getMessage());
        }
    }
}