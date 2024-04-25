package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.models.CharacterAnswer;
import hinc.come.guiltyornot.api.store.entities.CharacterAnswerEntity;
import hinc.come.guiltyornot.api.store.entities.CharacterEntity;
import hinc.come.guiltyornot.api.store.entities.CharacterQuestionEntity;
import hinc.come.guiltyornot.api.store.repositories.CharacterAnswerRepository;
import hinc.come.guiltyornot.api.store.repositories.CharacterQuestionRepository;
import hinc.come.guiltyornot.api.store.repositories.CharacterRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterAnswerService {
    @Autowired
    CharacterAnswerRepository characterAnswerRepository;
    @Autowired
    CharacterQuestionRepository characterQuestionRepository;
    @Autowired
    CharacterRepository characterRepository;

    public List<CharacterAnswer> getCharacterAnswers(Long questionId, Long characterId) throws NotFoundException {
        IsCharacterExist(characterId);
        IsQuestionExist(questionId);
        return CharacterAnswer.toModelList(characterAnswerRepository.findAllByCharacterQuestionId(questionId));
    }

    public CharacterAnswer createCharacterAnswer(
        Long questionId,
        CharacterAnswerEntity characterABody,
        Long characterId
    ) throws NotFoundException, MissingCredentialsException {
        IsCharacterExist(characterId);
        CharacterQuestionEntity existingQuestion = IsQuestionExist(questionId);

        if(characterABody.getText() == null || characterABody.getText().trim().isEmpty()){
            throw new MissingCredentialsException("Text is required!");
        }

        CharacterAnswerEntity characterAnswer = new CharacterAnswerEntity();
        characterAnswer.setCharacterQuestion(existingQuestion);
        characterAnswer.setCharacterQuestionId(questionId);
        characterAnswer.setText(characterABody.getText());
        return CharacterAnswer.toModel(characterAnswerRepository.save(characterAnswer));
    }

    public String deleteCharacterAnswer(
            Long questionId,
            Long answerId,
            Long characterId
    ) throws NotFoundException {
        IsCharacterExist(characterId);
        IsQuestionExist(questionId);
        IsAnswerExist(answerId);
        characterAnswerRepository.deleteByIdAndCharacterQuestionId(answerId, questionId);
        return "Answer with id \" " + answerId + "\" was deleted";
    }


    private CharacterQuestionEntity IsQuestionExist(Long questionId) throws  NotFoundException {
        Optional<CharacterQuestionEntity> characterQuestion = characterQuestionRepository.findById(questionId);
        if(characterQuestion.isEmpty()){
            throw new NotFoundException("Character with such Id doesn't exist!");
        }
        return characterQuestion.get();
    }

    private void IsCharacterExist(Long characterId) throws NotFoundException {
        Optional<CharacterEntity> character = characterRepository.findById(characterId);
        if(character.isEmpty()){
            throw new NotFoundException("Character with such id doesn't exist");
        }
    }

    private void IsAnswerExist(Long characterAnswerId) throws NotFoundException {
        Optional<CharacterAnswerEntity> characterAnswer = characterAnswerRepository.findById(characterAnswerId);
        if(characterAnswer.isEmpty()){
            throw new NotFoundException("Character with such Id doesn't exist!");
        }
    }
}
