package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.store.repositories.CharacterRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CharacterService {
    @Autowired
    CharacterRepository characterRepository;
}
