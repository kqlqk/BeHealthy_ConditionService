package me.kqlqk.behealthy.condition_service.controller.rest.v1;

import me.kqlqk.behealthy.condition_service.dto.UserPhotoDTO;
import me.kqlqk.behealthy.condition_service.service.UserPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserPhotoController {
    private final UserPhotoService userPhotoService;

    @Autowired
    public UserPhotoController(UserPhotoService userPhotoService) {
        this.userPhotoService = userPhotoService;
    }

    @PostMapping("/photo")
    public ResponseEntity<?> saveUserPhoto(@RequestParam long userId, @RequestBody @Valid UserPhotoDTO userPhotoDTO) {
        userPhotoDTO.setUserId(userId);
        userPhotoService.savePhoto(userPhotoDTO);

        return ResponseEntity.ok().build();
    }
}
