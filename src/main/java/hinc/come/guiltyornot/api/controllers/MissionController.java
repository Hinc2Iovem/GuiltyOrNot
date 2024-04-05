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

import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/missions")
public class MissionController {

    @Autowired
    MissionService missionService;

    public static final String SINGLE_MISSION = "/{missionId}";

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity getMissions(){
        try {
            Stream<MissionEntity> missions = missionService.getMissions();
            return ResponseEntity.ok().body(missions);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Something went wrong" + e.getMessage());
        }
    }

    @GetMapping(SINGLE_MISSION)
    public ResponseEntity getMissionById(
            @PathVariable(name = "missionId") Long missionId
    ) {
        try {
            MissionEntity mission = missionService.getMissionById(missionId);
            return ResponseEntity.ok().body(mission);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity createMission(@RequestBody MissionEntity missionBody){
        try {
            MissionEntity mission = missionService.createMission(missionBody);

            return ResponseEntity.ok().body(mission);
        } catch (MissingCredentialsException | BadRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
        }
    }

    @PatchMapping(SINGLE_MISSION)
    public ResponseEntity updateMission(
            @RequestBody MissionEntity missionBody,
            @PathVariable(name = "missionId") Long missionId
    ) {
        try {
            MissionEntity mission = missionService.updateMission(missionBody, missionId);
            return ResponseEntity.ok().body(mission);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
        }
    }


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
