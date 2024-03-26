package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.MissingCredentials;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
import hinc.come.guiltyornot.store.entities.QuestionEntity;
import hinc.come.guiltyornot.store.repositories.QuestionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public Stream<QuestionEntity> getQuestions() {
        return questionRepository.streamAllBy();
    };

    public QuestionEntity getQuestionById(Long questionId) throws NotFoundException {
        if(questionRepository.findById(questionId).isEmpty()) {
            throw new NotFoundException("Question with such Id doesn't exist");
        }

        return questionRepository.findById(questionId).get();
    }

    public QuestionEntity createQuestion(QuestionEntity questionBody) throws UserAlreadyExistException, MissingCredentials {
        if(questionRepository.findByTitle(questionBody.getTitle()) != null) {
            throw new UserAlreadyExistException("Such question already exists");
        }

        if(questionBody.getTitle().isEmpty() || questionBody.getText().isEmpty()){
            throw new MissingCredentials("Title and text are required");
        }

        return questionRepository.save(questionBody);
    }

    public QuestionEntity updateQuestion(
            QuestionEntity questionBody,
            Long questionId
    ) throws NotFoundException {
        if(questionRepository.findById(questionId).isEmpty()){
            throw new NotFoundException("Question with such Id doesn't exist");
        }
        QuestionEntity existingQuestion = questionRepository.findById(questionId).get();
        if(questionBody.getText() != null){
            existingQuestion.setText(questionBody.getText());
        }
        if(questionBody.getTitle() != null){
            existingQuestion.setTitle(questionBody.getTitle());
        }

        return questionRepository.save(existingQuestion);
    }

    public void deleteQuestion(Long questionId) throws NotFoundException {
        if(questionRepository.findById(questionId).isEmpty()){
            throw new NotFoundException("Question with such Id doesn't exist");
        }

        questionRepository.deleteById(questionId);
    }
}
