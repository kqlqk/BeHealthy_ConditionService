package me.kqlqk.behealthy.kcal_counter_service.controller.rest.v1;

import me.kqlqk.behealthy.kcal_counter_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.kcal_counter_service.exception.exceptions.UserConditionNotFound;
import me.kqlqk.behealthy.kcal_counter_service.model.KcalsInfo;
import me.kqlqk.behealthy.kcal_counter_service.service.UserConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserConditionRestController {
    private final UserConditionService userConditionService;

    @Autowired
    public UserConditionRestController(UserConditionService userConditionService) {
        this.userConditionService = userConditionService;
    }

    @PostMapping("/condition")
    public ResponseEntity<?> createUserCondition(@RequestBody UserConditionDTO userConditionDTO) {
        userConditionService.generateAndSaveCondition(
                userConditionDTO.getUserId(),
                userConditionDTO.getGender(),
                userConditionDTO.getAge(),
                userConditionDTO.getHeight(),
                userConditionDTO.getWeight(),
                userConditionDTO.getIntensity(),
                userConditionDTO.getGoal());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/condition")
    public UserConditionDTO getUserConditionByUserId(@RequestParam long userId) {
        if (!userConditionService.existsByUserId(userId)) {
            throw new UserConditionNotFound("User condition with userId = " + userId + " not found");
        }

        return UserConditionDTO.convertUserConditionToUserConditionDTO(userConditionService.getByUserId(userId));
    }

    @GetMapping("/kcals")
    public KcalsInfo getKcalsInfoByUserId(@RequestParam long userId) {
        return userConditionService.getKcalsInfoByUserId(userId);
    }
}
