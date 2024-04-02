package hinc.come.guiltyornot.api.services;

import hinc.come.guiltyornot.api.exceptions.NotFoundException;
import hinc.come.guiltyornot.api.store.entities.MissionEntity;
import hinc.come.guiltyornot.api.store.entities.SucceededMissionEntity;
import hinc.come.guiltyornot.api.store.entities.UserEntity;
import hinc.come.guiltyornot.api.store.repositories.MissionRepository;
import hinc.come.guiltyornot.api.store.repositories.SucceededMissionRepository;
import hinc.come.guiltyornot.api.store.repositories.UserRepository;
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

    @Autowired
    MissionRepository missionRepository;

    @Transactional(readOnly = true)
    public Stream<SucceededMissionEntity> getSucceededMissionsByUserId(Long userId) throws NotFoundException {
//        if(succeededMissionRepository.findById(userId).isEmpty()){
//            throw new NotFoundException("User with such id has failed this mission yet");
//        }
        return null;
    }

    public SucceededMissionEntity createSucceededMissionsByUserId(
            Long userId,
            Long missionId,
            SucceededMissionEntity succeededMissionBody
            ) throws NotFoundException {
            if(userRepository.findById(userId).isEmpty() || missionRepository.findById(missionId).isEmpty()){
                throw new NotFoundException("User or Mission with such id doesn't exist");
            }
            UserEntity currentUser = userRepository.findById(userId).get();
            MissionEntity currentMission = missionRepository.findById(missionId).get();

//            succeededMissionRepository.save(currentUser);
        return null;
    }
}
