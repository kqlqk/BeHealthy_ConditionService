package me.kqlqk.behealthy.condition_service.controller.rest.v1;

import me.kqlqk.behealthy.condition_service.dto.UserPhotoDTO;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserPhotoNotFoundException;
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


    @GetMapping("/photo")
    public UserPhotoDTO getUserPhotoByDate(@RequestParam long userId, @RequestParam String date) {
        if (!userPhotoService.existsByUserIdAndDate(userId, date)) {
            throw new UserPhotoNotFoundException("User photo with userId = " + userId + " and date = " + date + " not found");
        }

        return userPhotoService.getDTOWithEncodedPhoto(userId, date);
    }

    @PostMapping("/photo")
    public ResponseEntity<?> saveUserPhoto(@RequestParam long userId, @RequestBody @Valid UserPhotoDTO userPhotoDTO) {
        userPhotoDTO.setUserId(userId);
        userPhotoService.savePhoto(userPhotoDTO);

        return ResponseEntity.ok().build();
    }
}
