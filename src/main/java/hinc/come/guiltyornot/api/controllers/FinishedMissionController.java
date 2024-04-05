package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.services.FinishedMissionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/users/{userId}/finishedMissions")
public class FinishedMissionController {
    @Autowired
    FinishedMissionService finishedMissionService;

    private static final String ADD_FINISHED_MISSION = "/{missionId}";

    @PostMapping(ADD_FINISHED_MISSION)
    public ResponseEntity<String> addFinishedMission(
            @PathVariable Long missionId,
            @PathVariable Long userId
    ) throws BadRequestException {
        try {
            String res = finishedMissionService.addFinishedMission(missionId, userId);
        return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }
}
