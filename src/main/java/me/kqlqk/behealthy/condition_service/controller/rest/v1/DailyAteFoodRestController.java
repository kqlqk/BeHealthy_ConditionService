package me.kqlqk.behealthy.condition_service.controller.rest.v1;

import me.kqlqk.behealthy.condition_service.dto.daily_ate_food.AddDailyAteFoodDTO;
import me.kqlqk.behealthy.condition_service.dto.daily_ate_food.GetDailyAteFoodDTO;
import me.kqlqk.behealthy.condition_service.model.DailyAteFood;
import me.kqlqk.behealthy.condition_service.service.DailyAteFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DailyAteFoodRestController {
    private final DailyAteFoodService dailyAteFoodService;

    @Autowired
    public DailyAteFoodRestController(DailyAteFoodService dailyAteFoodService) {
        this.dailyAteFoodService = dailyAteFoodService;
    }

    @GetMapping("/food")
    public List<GetDailyAteFoodDTO> getDailyAteFood(@RequestParam long userId) {
        return GetDailyAteFoodDTO.convertList(dailyAteFoodService.getByUserId(userId));
    }

    @PostMapping("/food")
    public ResponseEntity<?> saveDailyAteFood(@RequestParam long userId, @RequestBody @Valid AddDailyAteFoodDTO addDailyAteFoodDTO) {
        DailyAteFood dailyAteFood = new DailyAteFood();
        dailyAteFood.setUserId(userId);
        dailyAteFood.setName(addDailyAteFoodDTO.getName());
        dailyAteFood.setWeight(addDailyAteFoodDTO.getWeight());
        dailyAteFood.setProtein(addDailyAteFoodDTO.getProtein());
        dailyAteFood.setFat(addDailyAteFoodDTO.getFat());
        dailyAteFood.setCarb(addDailyAteFoodDTO.getCarb());

        dailyAteFoodService.save(dailyAteFood);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/food")
    public ResponseEntity<?> deleteDailyAteFood(@RequestParam long productId, @RequestParam long userId) {
        dailyAteFoodService.delete(productId, userId);

        return ResponseEntity.ok().build();
    }

}
