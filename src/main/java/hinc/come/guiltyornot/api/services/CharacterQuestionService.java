package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.store.entities.CharacterEntity;
import hinc.come.guiltyornot.api.store.entities.CharacterQuestionEntity;
import hinc.come.guiltyornot.api.store.repositories.CharacterQuestionRepository;
import hinc.come.guiltyornot.api.store.repositories.CharacterRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterQuestionService {
    @Autowired
    CharacterQuestionRepository characterQuestionRepository;
    @Autowired
    CharacterRepository characterRepository;

    public Stream<CharacterQuestionEntity> getCharacterQuestions(Long characterId) throws NotFoundException {
        IsCharacterExist(characterId);
        return characterQuestionRepository.findAllByCharacterId(characterId);
    }
    public CharacterQuestionEntity createCharacterQuestion(
        Long characterId,
        CharacterQuestionEntity characterQBody
    ) throws NotFoundException, MissingCredentialsException {
        CharacterEntity existingCharacter = IsCharacterExist(characterId);

        if(characterQBody.getText() == null || characterQBody.getText().trim().isEmpty()){
            throw new MissingCredentialsException("Text is required!");
        }

        CharacterQuestionEntity characterQuestion = new CharacterQuestionEntity();
        characterQuestion.setCharacter(existingCharacter);
        characterQuestion.setCharacterId(characterId);
        characterQuestion.setText(characterQBody.getText());
        return characterQuestionRepository.save(characterQuestion);
    }

    public String deleteCharacterQuestion(
            Long characterId,
            Long questionId
    ) throws NotFoundException {
        IsCharacterExist(characterId);
        IsQuestionExist(questionId);
        characterQuestionRepository.deleteByIdAndCharacterId(questionId, characterId);
        return "Question with id \" " + questionId + "\" was deleted";
    }


    private CharacterEntity IsCharacterExist(Long characterId) throws NotFoundException {
        Optional<CharacterEntity> character = characterRepository.findById(characterId);
        if(character.isEmpty()){
            throw new NotFoundException("Character with such Id doesn't exist!");
        }
        return character.get();
    }

    private void IsQuestionExist(Long characterQuestionId) throws  NotFoundException {
        Optional<CharacterQuestionEntity> characterQuestion = characterQuestionRepository.findById(characterQuestionId);
        if(characterQuestion.isEmpty()){
            throw new NotFoundException("Character with such Id doesn't exist!");
        }
    }
}
