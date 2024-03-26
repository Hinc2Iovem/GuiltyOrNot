package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.services.QuestionService;
import hinc.come.guiltyornot.store.entities.QuestionEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity getQuestions() {
        try {
            Stream<QuestionEntity> questions = questionService.getQuestions();
            return ResponseEntity.ok().body(questions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong" + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity createQuestion(@RequestBody QuestionEntity questionBody) {
        try {
            QuestionEntity question = questionService.createQuestion(questionBody);
            return ResponseEntity.ok().body(question);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong" + e.getMessage());
        }
    }
}
