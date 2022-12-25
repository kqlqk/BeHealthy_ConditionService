package me.kqlqk.behealthy.condition_service.controller.rest.v1;

import me.kqlqk.behealthy.condition_service.dto.DailyAteFoodDTO;
import me.kqlqk.behealthy.condition_service.service.DailyAteFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<DailyAteFoodDTO> getDailyAteFoodForUser(@RequestParam("userId") long userId) {
        return DailyAteFoodDTO.convertListOfDailyAteFoodToListOFDailyAteFoodDTO(dailyAteFoodService.getByUserId(userId));
    }

    @PostMapping("/food")
    public ResponseEntity<?> addDailyAteFoodForUser(@RequestParam long userId, @RequestBody DailyAteFoodDTO dailyAteFoodDTO) {
        dailyAteFoodService.add(
                userId,
                dailyAteFoodDTO.getName(),
                dailyAteFoodDTO.getWeight(),
                dailyAteFoodDTO.getKcals(),
                dailyAteFoodDTO.getProteins(),
                dailyAteFoodDTO.getFats(),
                dailyAteFoodDTO.getCarbs());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/food")
    public ResponseEntity<?> deleteDailyAteFoodFromUser(@RequestParam("productId") long id) {
        dailyAteFoodService.delete(id);

        return ResponseEntity.ok().build();
    }

}
