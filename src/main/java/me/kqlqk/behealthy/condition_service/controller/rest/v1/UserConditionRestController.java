package me.kqlqk.behealthy.condition_service.controller.rest.v1;

import me.kqlqk.behealthy.condition_service.dto.DailyKcalsDTO;
import me.kqlqk.behealthy.condition_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.condition_service.dto.UserConditionWithoutFatPercentFemaleDTO;
import me.kqlqk.behealthy.condition_service.dto.UserConditionWithoutFatPercentMaleDTO;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionNotFoundException;
import me.kqlqk.behealthy.condition_service.model.DailyKcals;
import me.kqlqk.behealthy.condition_service.service.DailyKcalsService;
import me.kqlqk.behealthy.condition_service.service.UserConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserConditionRestController {
    private final UserConditionService userConditionService;
    private final DailyKcalsService dailyKcalsService;

    @Autowired
    public UserConditionRestController(UserConditionService userConditionService, DailyKcalsService dailyKcalsService) {
        this.userConditionService = userConditionService;
        this.dailyKcalsService = dailyKcalsService;
    }

    @PostMapping("/condition")
    public ResponseEntity<?> createUserCondition(@RequestParam long userId, @RequestBody UserConditionDTO userConditionDTO) {
        userConditionDTO.setUserId(userId);
        userConditionService.generateAndSaveCondition(userConditionDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/condition/male/fatPercent")
    public ResponseEntity<?> createUserConditionWithoutFatPercentMale(@RequestParam long userId,
                                                                      @RequestBody UserConditionWithoutFatPercentMaleDTO userConditionWithoutFatPercentMaleDTO) {
        userConditionWithoutFatPercentMaleDTO.setUserId(userId);
        userConditionService.generateAndSaveConditionWithoutFatPercent(userConditionWithoutFatPercentMaleDTO, null);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/condition/female/fatPercent")
    public ResponseEntity<?> createUserConditionWithoutFatPercentFemale(@RequestParam long userId,
                                                                        @RequestBody UserConditionWithoutFatPercentFemaleDTO userConditionWithoutFatPercentFemaleDTO) {
        userConditionWithoutFatPercentFemaleDTO.setUserId(userId);
        userConditionService.generateAndSaveConditionWithoutFatPercent(null, userConditionWithoutFatPercentFemaleDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/condition")
    public UserConditionDTO getUserConditionByUserId(@RequestParam long userId) {
        if (!userConditionService.existsByUserId(userId)) {
            throw new UserConditionNotFoundException("User condition with userId = " + userId + " not found");
        }

        return UserConditionDTO.convertUserConditionToUserConditionDTO(userConditionService.getByUserId(userId));
    }

    @PutMapping("/condition")
    public ResponseEntity<?> updateCondition(@RequestParam long userId, @RequestBody UserConditionDTO userConditionDTO) {
        userConditionDTO.setUserId(userId);
        userConditionService.updateCondition(userConditionDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/kcals")
    public DailyKcals getDailyKcalsByUserId(@RequestParam long userId) {
        return dailyKcalsService.getByUserId(userId);
    }

    @PutMapping("/kcals")
    public ResponseEntity<?> updateDailyKcalsByUserId(@RequestParam long userId, @RequestBody DailyKcalsDTO dailyKcalsDTO) {
        dailyKcalsService.updateDailyKcals(userId, dailyKcalsDTO);

        return ResponseEntity.ok().build();
    }
}
