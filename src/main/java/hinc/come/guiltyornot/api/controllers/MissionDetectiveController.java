package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.services.MissionDetectiveService;
import hinc.come.guiltyornot.api.store.entities.MissionDetectiveEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/detective/missions")
public class MissionDetectiveController {

    @Autowired
    MissionDetectiveService missionDetectiveService;
    public static final String CREATE_MISSION = "/users/{userId}";
    public static final String SINGLE_MISSION = "/{missionId}/users/{userId}";
    public static final String SINGLE_MISSION_BY_ID = "/{missionId}";
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<MissionDetectiveEntity>> getMissions() throws BadRequestException {
        try {
            List<MissionDetectiveEntity> missions = missionDetectiveService.getMissions();
            return ResponseEntity.ok().body(missions);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @GetMapping(SINGLE_MISSION_BY_ID)
    public ResponseEntity<MissionDetectiveEntity> getMissionById(
            @PathVariable(name = "missionId") Long missionId
    ) throws BadRequestException {
        try {
            MissionDetectiveEntity mission = missionDetectiveService.getMissionById(missionId);
            return ResponseEntity.ok().body(mission);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }


    @PostMapping(CREATE_MISSION)
    public ResponseEntity<MissionDetectiveEntity> createMission(
            @RequestBody MissionDetectiveEntity missionBody,
            @PathVariable Long userId
    ) throws BadRequestException {
        try {
            MissionDetectiveEntity mission = missionDetectiveService.createMission(missionBody, userId);
            return ResponseEntity.ok().body(mission);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PatchMapping(SINGLE_MISSION)
    public ResponseEntity<MissionDetectiveEntity> updateMission(
            @RequestBody MissionDetectiveEntity missionBody,
            @PathVariable(name = "missionId") Long missionId,
            @PathVariable Long userId
    ) throws BadRequestException {
        try {
            MissionDetectiveEntity mission = missionDetectiveService.updateMission(missionBody, missionId, userId);
            return ResponseEntity.ok().body(mission);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }


    @DeleteMapping(SINGLE_MISSION_BY_ID)
    public ResponseEntity<String> deleteMission(@PathVariable(name = "missionId") Long missionId) {
        try {
            missionDetectiveService.deleteMission(missionId);
            return ResponseEntity.ok("Mission with id: " + missionId + " was deleted");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
        }
    }
}
