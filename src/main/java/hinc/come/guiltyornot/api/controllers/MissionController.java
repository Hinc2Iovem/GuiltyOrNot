package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.exceptions.MissingCredentials;
import hinc.come.guiltyornot.api.services.MissionService;
import hinc.come.guiltyornot.store.entities.MissionEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/missions")
public class MissionController {

    @Autowired
    MissionService missionService;

//    public static final String SIGN_UP = "/registration";
    @GetMapping
    public ResponseEntity getMissions(){
        try {
            Stream<MissionEntity> missions = missionService.getMissions();
            return ResponseEntity.ok().body(missions);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @PostMapping
    public ResponseEntity createMission(@RequestBody MissionEntity missionBody){
        try {
            MissionEntity mission = missionService.createMission(missionBody);
            return ResponseEntity.ok().body(mission);
        } catch (MissingCredentials | BadRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }
}
