package me.kqlqk.behealthy.condition_service.controller.rest.v1;

import me.kqlqk.behealthy.condition_service.dto.DailyFoodDTO;
import me.kqlqk.behealthy.condition_service.model.DailyFood;
import me.kqlqk.behealthy.condition_service.service.DailyFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/daily_food")
public class DailyFoodRestController {
    private final DailyFoodService dailyFoodService;

    @Autowired
    public DailyFoodRestController(DailyFoodService dailyFoodService) {
        this.dailyFoodService = dailyFoodService;
    }

    @GetMapping
    public List<DailyFood> getDailyFoodForUser(@RequestParam("userId") long userId) {
        return dailyFoodService.getByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<?> addDailyFoodForUser(@RequestBody DailyFoodDTO dailyFoodDTO) {
        dailyFoodService.add(
                dailyFoodDTO.getUserId(),
                dailyFoodDTO.getName(),
                dailyFoodDTO.getWeight(),
                dailyFoodDTO.getKcals(),
                dailyFoodDTO.getProteins(),
                dailyFoodDTO.getFats(),
                dailyFoodDTO.getCarbs());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDailyFoodFromUser(@RequestParam("productId") long id) {
        dailyFoodService.delete(id);

        return ResponseEntity.ok().build();
    }

}
