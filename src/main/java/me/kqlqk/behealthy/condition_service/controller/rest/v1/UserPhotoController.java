package me.kqlqk.behealthy.condition_service.controller.rest.v1;

import me.kqlqk.behealthy.condition_service.dto.user_photo.AddUserPhotoDTO;
import me.kqlqk.behealthy.condition_service.dto.user_photo.FullUserPhotoDTO;
import me.kqlqk.behealthy.condition_service.dto.user_photo.GetEncodedPhoto;
import me.kqlqk.behealthy.condition_service.model.UserPhoto;
import me.kqlqk.behealthy.condition_service.service.UserPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserPhotoController {
    private final UserPhotoService userPhotoService;

    @Autowired
    public UserPhotoController(UserPhotoService userPhotoService) {
        this.userPhotoService = userPhotoService;
    }


    @GetMapping("/photo")
    public GetEncodedPhoto getEncodedPhotoByDate(@RequestParam long userId, @RequestParam String date) {
        GetEncodedPhoto getEncodedPhoto = new GetEncodedPhoto();
        getEncodedPhoto.setEncodedPhoto(userPhotoService.getEncodedPhoto(userId, date));

        return getEncodedPhoto;
    }

    @GetMapping("/photo/all")
    public List<FullUserPhotoDTO> getAllPhotosAndFiles(@RequestParam long userId) {
        List<UserPhoto> userPhotos = userPhotoService.getByUserId(userId);

        List<FullUserPhotoDTO> res = new ArrayList<>();

        userPhotos.forEach(e -> res.add(new FullUserPhotoDTO(e.getPhotoPath(),
                                                             e.getPhotoDate(),
                                                             userPhotoService.getEncodedPhoto(userId, e.getPhotoDate()))));

        return res;
    }

    @PostMapping("/photo")
    public ResponseEntity<?> saveUserPhoto(@RequestParam long userId, @RequestBody @Valid AddUserPhotoDTO addUserPhotoDTO) {
        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setUserId(userId);
        userPhoto.setPhotoDate(userPhotoService.convertStringToDate(addUserPhotoDTO.getPhotoDate()));

        userPhotoService.save(userPhoto, addUserPhotoDTO.getEncodedPhoto());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/photo")
    public ResponseEntity<?> deleteUserPhoto(@RequestParam long userId, @RequestParam(required = false) String date) {
        if (date != null) {
            userPhotoService.deleteByUserIdAndDate(userId, date);
        }
        else {
            userPhotoService.deleteByUserId(userId);
        }

        return ResponseEntity.ok().build();
    }

}
