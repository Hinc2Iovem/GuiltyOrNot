package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.store.entities.CharacterAnswerEntity;
import hinc.come.guiltyornot.api.store.entities.CharacterEntity;
import hinc.come.guiltyornot.api.store.entities.CharacterQuestionEntity;
import hinc.come.guiltyornot.api.store.repositories.CharacterAnswerRepository;
import hinc.come.guiltyornot.api.store.repositories.CharacterQuestionRepository;
import hinc.come.guiltyornot.api.store.repositories.CharacterRepository;
import hinc.come.guiltyornot.api.store.repositories.QuestionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterAnswerService {
    @Autowired
    CharacterAnswerRepository characterAnswerRepository;
    @Autowired
    CharacterQuestionRepository characterQuestionRepository;

    public Stream<CharacterAnswerEntity> getCharacterAnswers(Long questionId) throws NotFoundException {
        IsQuestionExist(questionId);
        return characterAnswerRepository.findAllByCharacterQuestionId(questionId);
    }
    public CharacterAnswerEntity createCharacterAnswer(
        Long questionId,
        CharacterAnswerEntity characterABody
    ) throws NotFoundException, MissingCredentialsException {
        CharacterQuestionEntity existingQuestion = IsQuestionExist(questionId);

        if(characterABody.getText() == null || characterABody.getText().trim().isEmpty()){
            throw new MissingCredentialsException("Text is required!");
        }

        CharacterAnswerEntity characterAnswer = new CharacterAnswerEntity();
        characterAnswer.setCharacterQuestion(existingQuestion);
        characterAnswer.setCharacterQuestionId(questionId);
        characterAnswer.setText(characterAnswer.getText());
        return characterAnswerRepository.save(characterAnswer);
    }

    public String deleteCharacterAnswer(
            Long questionId,
            Long answerId
    ) throws NotFoundException {
        IsQuestionExist(questionId);
        IsAnswerExist(answerId);
        characterAnswerRepository.deleteByIdAndCharacterQuestionId(answerId, questionId);
        return "Answer with id \" " + answerId + "\" was deleted";
    }


    private CharacterQuestionEntity IsQuestionExist(Long characterQuestionId) throws  NotFoundException {
        Optional<CharacterQuestionEntity> characterQuestion = characterQuestionRepository.findById(characterQuestionId);
        if(characterQuestion.isEmpty()){
            throw new NotFoundException("Character with such Id doesn't exist!");
        }
        return characterQuestion.get();
    }

    private void IsAnswerExist(Long characterAnswerId) throws  NotFoundException {
        Optional<CharacterAnswerEntity> characterAnswer = characterAnswerRepository.findById(characterAnswerId);
        if(characterAnswer.isEmpty()){
            throw new NotFoundException("Character with such Id doesn't exist!");
        }
    }
}
