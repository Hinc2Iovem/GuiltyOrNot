package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.domains.AnswerTypes;
import hinc.come.guiltyornot.api.domains.UserRoles;
import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
import hinc.come.guiltyornot.api.store.entities.AnswerEntity;
import hinc.come.guiltyornot.api.store.entities.MissionEntity;
import hinc.come.guiltyornot.api.store.entities.QuestionEntity;
import hinc.come.guiltyornot.api.store.repositories.AnswerRepository;
import hinc.come.guiltyornot.api.store.repositories.MissionRepository;
import hinc.come.guiltyornot.api.store.repositories.QuestionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerService {
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<AnswerEntity> getAnswers() {
        return answerRepository.streamAllBy().toList();
    };

    public List<AnswerEntity> getAnswersByQuestionId(
            Long QuestionId
    ) throws NotFoundException {
        List<AnswerEntity> answersByQuestionId = answerRepository.findAllByQuestionId(QuestionId);
        if(answersByQuestionId.isEmpty()) {
            throw new NotFoundException("There are no answers for question with id: " + QuestionId);
        }
        return answersByQuestionId;
    }

    public AnswerEntity getAnswerById(Long answerId) throws NotFoundException {
        if(answerRepository.findById(answerId).isEmpty()) {
            throw new NotFoundException("Answer with such Id doesn't exist");
        }
        return answerRepository.findById(answerId).get();
    }

    public AnswerEntity createAnswer(AnswerEntity answerBody, Long questionId) throws UserAlreadyExistException, MissingCredentialsException, NotFoundException, BadRequestException {
        if(answerRepository.findByText(answerBody.getText()) != null) {
            throw new UserAlreadyExistException("Such answer already exists");
        }

        if(questionId == null
            || answerBody.getText().trim().isEmpty()
            || answerBody.getType().trim().isEmpty()
            || answerBody.getPoints() == 0){
            throw new MissingCredentialsException("Text, type, points and questionId are required");
        }


        if(questionRepository.findById(questionId).isEmpty()){
            throw new NotFoundException("Question with such id doesn't exist.");
        }

        if(answerBody.getPoints() > 10) {
            throw new BadRequestException("points can be from 1 to 10");
        }

        QuestionEntity currentQuestion = questionRepository.findById(questionId).get();
        AnswerEntity currentAnswer = new AnswerEntity();

        currentAnswer.setQuestion(currentQuestion);
        currentAnswer.setQuestionId(currentQuestion.getId());
        currentAnswer.setText(answerBody.getText());
        currentAnswer.setPoints(answerBody.getPoints());
        currentAnswer.setType(answerBody.getType());
        return answerRepository.save(currentAnswer);
    }

    public AnswerEntity updateAnswer(
            AnswerEntity answerBody,
            Long answerId
    ) throws NotFoundException, BadRequestException {
        if(answerRepository.findById(answerId).isEmpty()){
            throw new NotFoundException("Answer with such Id doesn't exist");
        }
        AnswerEntity existingAnswer = answerRepository.findById(answerId).get();
        if(answerBody.getText() != null && !answerBody.getText().trim().isEmpty()){
            existingAnswer.setText(answerBody.getText());
        }
        if(answerBody.getPoints() != null){
            if(answerBody.getPoints() > 10) {
                throw new BadRequestException("points can be from 1 to 10");
            }
            existingAnswer.setPoints(answerBody.getPoints());
        }
        if(answerBody.getType() != null && !answerBody.getType().trim().isEmpty()){
            existingAnswer.setType(answerBody.getType());
        }

        return answerRepository.save(existingAnswer);
    }

    public void deleteAnswer(Long answerId) throws NotFoundException {
        if(answerRepository.findById(answerId).isEmpty()){
            throw new NotFoundException("Answer with such Id doesn't exist");
        }

        answerRepository.deleteById(answerId);
    }
}