package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.domains.UserRoles;
import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
import hinc.come.guiltyornot.api.models.Mission;
import hinc.come.guiltyornot.api.services.MissionService;
import hinc.come.guiltyornot.api.store.entities.MissionEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/missions")
public class MissionController {

    @Autowired
    MissionService missionService;
    public static final String CREATE_MISSION = "/users/{userId}";
    public static final String SINGLE_MISSION = "/{missionId}/users/{userId}";
    public static final String SINGLE_MISSION_BY_ID = "/{missionId}";
    public static final String MISSIONS_BY_ROLE = "/roles/{role}";


    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<Mission>> getMissions() throws BadRequestException {
        try {
            List<Mission> missions = missionService.getMissions();
            return ResponseEntity.ok().body(missions);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @GetMapping(MISSIONS_BY_ROLE)
    @Transactional(readOnly = true)
    public ResponseEntity<List<Mission>> getMissionsByRole(
            @PathVariable(name = "role") String role
    ) throws BadRequestException {
        try {
            UserRoles.valueOf(role.toUpperCase());
            List<Mission> missions = missionService.getMissionsByRole(role);
            return ResponseEntity.ok().body(missions);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @GetMapping(SINGLE_MISSION_BY_ID)
    public ResponseEntity<Mission> getMissionById(
            @PathVariable(name = "missionId") Long missionId
    ) throws BadRequestException {
        try {
            Mission mission = missionService.getMissionById(missionId);
            return ResponseEntity.ok().body(mission);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PostMapping(CREATE_MISSION)
    public ResponseEntity<Mission> createMission(
            @RequestBody MissionEntity missionBody,
            @PathVariable Long userId
    ) throws BadRequestException {
        try {
            Mission mission = missionService.createMission(missionBody, userId);
            return ResponseEntity.ok().body(mission);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @PatchMapping(SINGLE_MISSION)
    public ResponseEntity<Mission> updateMission(
            @RequestBody MissionEntity missionBody,
            @PathVariable(name = "missionId") Long missionId,
            @PathVariable Long userId
    ) throws BadRequestException {
        try {
            Mission mission = missionService.updateMission(missionBody, missionId, userId);
            return ResponseEntity.ok().body(mission);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }


    @DeleteMapping(SINGLE_MISSION_BY_ID)
    public ResponseEntity<String> deleteMission(@PathVariable(name = "missionId") Long missionId) {
        try {
            missionService.deleteMission(missionId);
            return ResponseEntity.ok("Mission with id: " + missionId + " was deleted");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
        }
    }
}
