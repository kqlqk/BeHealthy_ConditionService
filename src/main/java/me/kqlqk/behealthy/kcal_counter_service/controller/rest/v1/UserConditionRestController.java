package me.kqlqk.behealthy.kcal_counter_service.controller.rest.v1;

import me.kqlqk.behealthy.kcal_counter_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.kcal_counter_service.service.UserConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserConditionRestController {
    private final UserConditionService userConditionService;

    @Autowired
    public UserConditionRestController(UserConditionService userConditionService) {
        this.userConditionService = userConditionService;
    }

    @PostMapping("/create_condition")
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
}
