package me.kqlqk.behealthy.condition_service.controller.rest.v1;

import me.kqlqk.behealthy.condition_service.dto.user_kcal.AddUpdateUserKcalDTO;
import me.kqlqk.behealthy.condition_service.dto.user_kcal.GetUserKcalDTO;
import me.kqlqk.behealthy.condition_service.model.UserKcal;
import me.kqlqk.behealthy.condition_service.service.UserKcalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserKcalRestController {
    private final UserKcalService userKcalService;

    public UserKcalRestController(UserKcalService userKcalService) {
        this.userKcalService = userKcalService;
    }

    @GetMapping("/kcal/user")
    public GetUserKcalDTO getUserKcalByUserId(@RequestParam long userId) {
        return GetUserKcalDTO.convert(userKcalService.getByUserId(userId));
    }

    @PostMapping("/kcal/user")
    public ResponseEntity<?> createUserKcal(@RequestParam long userId, @RequestBody @Valid AddUpdateUserKcalDTO addUserKcalDTO) {
        UserKcal userKcal = new UserKcal();
        userKcal.setUserId(userId);
        userKcal.setKcal(addUserKcalDTO.getKcal());
        userKcal.setProtein(addUserKcalDTO.getProtein());
        userKcal.setFat(addUserKcalDTO.getFat());
        userKcal.setCarb(addUserKcalDTO.getCarb());
        userKcal.setOnlyKcal(addUserKcalDTO.isOnlyKcal());
        userKcal.setInPriority(addUserKcalDTO.isInPriority());

        userKcalService.save(userKcal);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/kcal/user")
    public ResponseEntity<?> updateUserKcal(@RequestParam long userId, @RequestBody @Valid AddUpdateUserKcalDTO updateUserKcalDTO) {
        UserKcal userKcal = userKcalService.getByUserId(userId);
        userKcal.setKcal(updateUserKcalDTO.getKcal());
        userKcal.setProtein(updateUserKcalDTO.getProtein());
        userKcal.setFat(updateUserKcalDTO.getFat());
        userKcal.setCarb(updateUserKcalDTO.getCarb());
        userKcal.setOnlyKcal(updateUserKcalDTO.isOnlyKcal());
        userKcal.setInPriority(updateUserKcalDTO.isInPriority());

        userKcalService.update(userKcal);

        return ResponseEntity.ok().build();
    }
}
