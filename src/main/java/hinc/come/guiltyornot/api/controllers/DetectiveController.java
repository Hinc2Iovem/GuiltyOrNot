package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.models.Detective;
import hinc.come.guiltyornot.api.services.DetectiveService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/detectives")
public class DetectiveController {
    @Autowired
    DetectiveService detectiveService;

    public static final String SINGLE_DETECTIVE = "/{detectiveId}";

    @PatchMapping(SINGLE_DETECTIVE)
    public ResponseEntity<Detective> uploadImgUrl(
            @PathVariable Long detectiveId,
            @RequestBody String imgUrl
            ) throws BadRequestException {
        try {
            Detective detective = detectiveService.uploadImgUrl(imgUrl, detectiveId);
            return ResponseEntity.ok().body(detective);
        } catch (Exception e){
            throw new BadRequestException("Something went wrong " + e.getMessage());
        }
    }
}
