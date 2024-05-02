package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.models.CharacterGuilty;
import hinc.come.guiltyornot.api.models.CharacterVictim;
import hinc.come.guiltyornot.api.services.CharacterGuiltyService;
import hinc.come.guiltyornot.api.services.CharacterVictimService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/characters/victim/missions")
public class CharacterVictimController {
    @Autowired
    CharacterVictimService characterVictimService;
    public static final String SINGLE_CHARACTER_BY_MISSION_ID = "/{missionId}";

    @GetMapping(SINGLE_CHARACTER_BY_MISSION_ID)
    public ResponseEntity<CharacterVictim> getGuilty(
            @PathVariable(name = "missionId") Long missionId
    ) throws BadRequestException {
        try {
            CharacterVictim res = characterVictimService.getVictim(missionId);
            return ResponseEntity.ok().body(res);
        } catch(Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }
}
