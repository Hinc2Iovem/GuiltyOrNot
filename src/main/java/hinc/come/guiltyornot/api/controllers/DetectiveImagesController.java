package hinc.come.guiltyornot.api.controllers;

import hinc.come.guiltyornot.api.exceptions.BadRequestException;
import hinc.come.guiltyornot.api.models.DetectiveImages;
import hinc.come.guiltyornot.api.services.DetectiveImagesService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/images/detectives")
public class DetectiveImagesController {
    @Autowired
    DetectiveImagesService detectiveImagesService;

    public static final String SINGLE_DETECTIVE = "/{detectiveId}";

    @GetMapping(SINGLE_DETECTIVE)
    public ResponseEntity<List<DetectiveImages>> getAllImages(@PathVariable Long detectiveId) throws BadRequestException {
        try {
            List<DetectiveImages> images = detectiveImagesService.getAllImages(detectiveId);
            return ResponseEntity.ok().body(images);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong: " + e.getMessage());
        }
    }
}
