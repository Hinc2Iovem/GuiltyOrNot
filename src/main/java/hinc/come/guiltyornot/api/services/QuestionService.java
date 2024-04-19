package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.exceptions.UserAlreadyExistException;
import hinc.come.guiltyornot.api.models.Question;
import hinc.come.guiltyornot.api.store.entities.MissionEntity;
import hinc.come.guiltyornot.api.store.entities.QuestionEntity;
import hinc.come.guiltyornot.api.store.repositories.MissionRepository;
import hinc.come.guiltyornot.api.store.repositories.QuestionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    MissionRepository missionRepository;

    @Transactional(readOnly = true)
    public List<QuestionEntity> getQuestions() {
        return questionRepository.streamAllBy().toList();
    };

    public List<QuestionEntity> getQuestionsByMissionId(
            Long missionId
    ) throws NotFoundException {
       List<QuestionEntity> questionsByMissionId = questionRepository.findAllByMissionId(missionId);
       if(questionsByMissionId.isEmpty()) {
        throw new NotFoundException("There are no questions for mission with id: " + missionId);
       }

       return questionsByMissionId;
    }

    public QuestionEntity getQuestionById(Long questionId) throws NotFoundException {
        if(questionRepository.findById(questionId).isEmpty()) {
            throw new NotFoundException("Question with such Id doesn't exist");
        }

        return questionRepository.findById(questionId).get();
    }

    public QuestionEntity createQuestion(QuestionEntity questionBody) throws UserAlreadyExistException, MissingCredentialsException, NotFoundException {
        if(questionRepository.findByTitle(questionBody.getTitle()) != null) {
            throw new UserAlreadyExistException("Such question already exists");
        }

        if(questionBody.getTitle().isEmpty() || questionBody.getMissionId() == null){
            throw new MissingCredentialsException("Title, text and missionId are required");
        }

        if(missionRepository.findById(questionBody.getMissionId()).isEmpty()){
            throw new NotFoundException("Mission with such id doesn't exist.");
        }

        MissionEntity currentMission = missionRepository.findById(questionBody.getMissionId()).get();
        QuestionEntity currentQuestion = new QuestionEntity();


        currentQuestion.setMission(currentMission);
        currentQuestion.setMissionId(currentMission.getId());
        currentQuestion.setTitle(questionBody.getTitle());
        return questionRepository.save(currentQuestion);
    }

    public QuestionEntity updateQuestion(
            QuestionEntity questionBody,
            Long questionId
    ) throws NotFoundException {
        if(questionRepository.findById(questionId).isEmpty()){
            throw new NotFoundException("Question with such Id doesn't exist");
        }
        QuestionEntity existingQuestion = questionRepository.findById(questionId).get();

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
