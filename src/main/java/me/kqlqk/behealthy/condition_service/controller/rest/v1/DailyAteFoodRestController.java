package me.kqlqk.behealthy.condition_service.controller.rest.v1;

import me.kqlqk.behealthy.condition_service.dto.daily_ate_food.AddUpdateDailyAteFoodDTO;
import me.kqlqk.behealthy.condition_service.dto.daily_ate_food.GetDailyAteFoodDTO;
import me.kqlqk.behealthy.condition_service.model.DailyAteFood;
import me.kqlqk.behealthy.condition_service.service.DailyAteFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class DailyAteFoodRestController {
    private final DailyAteFoodService dailyAteFoodService;

    @Autowired
    public DailyAteFoodRestController(DailyAteFoodService dailyAteFoodService) {
        this.dailyAteFoodService = dailyAteFoodService;
    }

    @GetMapping("/food")
    public ResponseEntity<?> getDailyAteFood(@RequestParam long userId, @RequestParam(required = false) String productName) {
        if (productName != null) {
            return ResponseEntity.ok(GetDailyAteFoodDTO.convert(dailyAteFoodService.getByNameAndUserId(productName, userId)));
        }

        return ResponseEntity.ok(GetDailyAteFoodDTO.convertList(dailyAteFoodService.getByUserId(userId)));
    }

    @PostMapping("/food")
    public ResponseEntity<?> saveDailyAteFood(@RequestParam long userId, @RequestBody @Valid AddUpdateDailyAteFoodDTO addUpdateDailyAteFoodDTO) {
        DailyAteFood dailyAteFood = new DailyAteFood();
        dailyAteFood.setUserId(userId);
        dailyAteFood.setName(addUpdateDailyAteFoodDTO.getName());
        dailyAteFood.setWeight(addUpdateDailyAteFoodDTO.getWeight());
        dailyAteFood.setProtein(addUpdateDailyAteFoodDTO.getProtein());
        dailyAteFood.setFat(addUpdateDailyAteFoodDTO.getFat());
        dailyAteFood.setCarb(addUpdateDailyAteFoodDTO.getCarb());
        dailyAteFood.setToday(addUpdateDailyAteFoodDTO.isToday());

        dailyAteFoodService.save(dailyAteFood);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/food")
    public ResponseEntity<?> updateDailyAteFood(@RequestParam long userId, @RequestBody @Valid AddUpdateDailyAteFoodDTO addUpdateDailyAteFoodDTO) {
        DailyAteFood dailyAteFood = dailyAteFoodService.getByNameAndUserId(addUpdateDailyAteFoodDTO.getName(), userId);

        dailyAteFood.setWeight(addUpdateDailyAteFoodDTO.getWeight());
        dailyAteFood.setProtein(addUpdateDailyAteFoodDTO.getProtein());
        dailyAteFood.setFat(addUpdateDailyAteFoodDTO.getFat());
        dailyAteFood.setCarb(addUpdateDailyAteFoodDTO.getCarb());
        dailyAteFood.setToday(addUpdateDailyAteFoodDTO.isToday());

        dailyAteFoodService.update(dailyAteFood);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/food")
    public ResponseEntity<?> deleteDailyAteFood(@RequestParam String productName, @RequestParam long userId) {
        dailyAteFoodService.delete(productName, userId);

        return ResponseEntity.ok().build();
    }

}
