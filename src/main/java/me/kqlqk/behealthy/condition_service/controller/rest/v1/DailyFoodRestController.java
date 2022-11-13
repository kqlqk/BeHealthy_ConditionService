package me.kqlqk.behealthy.condition_service.controller.rest.v1;

import me.kqlqk.behealthy.condition_service.dto.DailyFoodDTO;
import me.kqlqk.behealthy.condition_service.service.DailyFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DailyFoodRestController {
    private final DailyFoodService dailyFoodService;

    @Autowired
    public DailyFoodRestController(DailyFoodService dailyFoodService) {
        this.dailyFoodService = dailyFoodService;
    }

    @GetMapping("/food")
    public List<DailyFoodDTO> getDailyFoodForUser(@RequestParam("userId") long userId) {
        return DailyFoodDTO.convertListOfDailyFoodToListOFDailyFoodDTO(dailyFoodService.getByUserId(userId));
    }

    @PostMapping("/food")
    public ResponseEntity<?> addDailyFoodForUser(@RequestParam long userId, @RequestBody DailyFoodDTO dailyFoodDTO) {
        dailyFoodService.add(
                userId,
                dailyFoodDTO.getName(),
                dailyFoodDTO.getWeight(),
                dailyFoodDTO.getKcals(),
                dailyFoodDTO.getProteins(),
                dailyFoodDTO.getFats(),
                dailyFoodDTO.getCarbs());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/food")
    public ResponseEntity<?> deleteDailyFoodFromUser(@RequestParam("productId") long id) {
        dailyFoodService.delete(id);

        return ResponseEntity.ok().build();
    }

}
