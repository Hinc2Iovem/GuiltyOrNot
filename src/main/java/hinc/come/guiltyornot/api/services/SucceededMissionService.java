package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.store.entities.SucceededMissionEntity;
import hinc.come.guiltyornot.store.entities.UserEntity;
import hinc.come.guiltyornot.store.repositories.SucceededMissionRepository;
import hinc.come.guiltyornot.store.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class SucceededMissionService {
    @Autowired
    SucceededMissionRepository succeededMissionRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public Stream<SucceededMissionEntity> getSucceededMissionsByUserId(Long userId) throws NotFoundException {
//        if(succeededMissionRepository.findById(userId).isEmpty()){
//            throw new NotFoundException("User with such id has failed this mission yet");
//        }
        return null;
    }

    public SucceededMissionEntity createSucceededMissionsByUserId(
            Long userId,
            SucceededMissionEntity succeededMissionBody
            ) {
//            if(succeededMissionBody.)
        return null;
    }
}
