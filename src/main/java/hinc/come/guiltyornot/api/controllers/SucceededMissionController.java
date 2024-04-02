package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.services.SucceededMissionService;
import hinc.come.guiltyornot.api.store.entities.SucceededMissionEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/succeededMissions/{userId}")
public class SucceededMissionController {
    @Autowired
    SucceededMissionService succeededMissionService;

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity getSucceededMissionsByUserId(
            @PathVariable(name = "userId") Long userId
    ) {
        try {
            Stream<SucceededMissionEntity> succededMissions = succeededMissionService.getSucceededMissionsByUserId(userId);
            return ResponseEntity.ok().body(succededMissions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity createSucceededMissionsByUserId(
            @PathVariable(name = "userId") Long userId,
            @RequestBody SucceededMissionEntity succeededMissionBody,
            @RequestBody Long missionId
    ) {
        try {
            SucceededMissionEntity succededMission = succeededMissionService.createSucceededMissionsByUserId(userId, missionId, succeededMissionBody);
            return ResponseEntity.ok().body(succededMission);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
        }
    }
}
