package hinc.come.guiltyornot.api.services;

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

    public QuestionEntity getQuestionById(Long questionId) {
        return null;
    }

    public QuestionEntity createQuestion(QuestionEntity questionBody){
        return null;
    }

    public QuestionEntity updateQuestion(
            QuestionEntity questionBody,
            Long questionId
    ) {
        return null;
    }

    public void deleteQuestion(Long questionId) {}
}
