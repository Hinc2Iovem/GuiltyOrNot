package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.models.DetectiveImages;
import hinc.come.guiltyornot.api.models.MissionDetectiveCharacters;
import hinc.come.guiltyornot.api.services.DetectiveImagesService;
import hinc.come.guiltyornot.api.services.MissionDetectiveCharactersService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/detectives/missions")
public class MissionDetectiveCharactersController {
    @Autowired
    MissionDetectiveCharactersService missionDetectiveCharactersService;

    public static final String SINGLE_MISSION = "/{missionId}";

    @GetMapping(SINGLE_MISSION)
    public ResponseEntity<List<MissionDetectiveCharacters>> getAllCharacterIds(@PathVariable Long missionId) throws BadRequestException {
        try {
            List<MissionDetectiveCharacters> images = missionDetectiveCharactersService.getAllCharacterIds(missionId);
            return ResponseEntity.ok().body(images);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }
}
