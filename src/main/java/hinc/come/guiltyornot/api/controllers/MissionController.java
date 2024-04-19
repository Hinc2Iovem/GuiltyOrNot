package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.domains.UserRoles;
import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentialsException;
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

    public static final String SINGLE_MISSION = "/{missionId}";
    public static final String MISSIONS_BY_ROLE = "/roles/{role}";


    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<Stream<MissionEntity>> getMissions() throws BadRequestException {
        try {
            Stream<MissionEntity> missions = missionService.getMissions();
            return ResponseEntity.ok().body(missions);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(MISSIONS_BY_ROLE)
    @Transactional(readOnly = true)
    public ResponseEntity<Stream<MissionEntity>> getMissionsByRole(
            @PathVariable(name = "role") String role
    ) throws BadRequestException {
        try {
            UserRoles.valueOf(role.toUpperCase());
            Stream<MissionEntity> missions = missionService.getMissionsByRole(role);
            return ResponseEntity.ok().body(missions);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(SINGLE_MISSION)
    public ResponseEntity<MissionEntity> getMissionById(
            @PathVariable(name = "missionId") Long missionId
    ) throws BadRequestException {
        try {
            MissionEntity mission = missionService.getMissionById(missionId);
            return ResponseEntity.ok().body(mission);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public ResponseEntity<MissionEntity> createMission(@RequestBody MissionEntity missionBody) throws BadRequestException {
        try {
            MissionEntity mission = missionService.createMission(missionBody);
            return ResponseEntity.ok().body(mission);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PatchMapping(SINGLE_MISSION)
    public ResponseEntity<MissionEntity> updateMission(
            @RequestBody MissionEntity missionBody,
            @PathVariable(name = "missionId") Long missionId
    ) throws BadRequestException {
        try {
            MissionEntity mission = missionService.updateMission(missionBody, missionId);
            return ResponseEntity.ok().body(mission);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }


    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping(SINGLE_MISSION)
    public ResponseEntity deleteMission(@PathVariable(name = "missionId") Long missionId) {
        try {
            missionService.deleteMission(missionId);
            return ResponseEntity.ok("Mission with id: " + missionId + " was deleted");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
        }
    }
}
