package me.kqlqk.behealthy.condition_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.DailyAteFood;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyAteFoodDTO {
    private long id;
    private String name;
    private double weight;
    private double kcals;
    private double proteins;
    private double fats;
    private double carbs;

    public static DailyAteFoodDTO convertDailyAteFoodToDailyAteFoodDTO(DailyAteFood dailyAteFood) {
        return new DailyAteFoodDTO(
                dailyAteFood.getId(),
                dailyAteFood.getName(),
                dailyAteFood.getWeight(),
                dailyAteFood.getKcals(),
                dailyAteFood.getProteins(),
                dailyAteFood.getFats(),
                dailyAteFood.getCarbs()
        );
    }

    public static List<DailyAteFoodDTO> convertListOfDailyAteFoodToListOfDailyAteFoodDTO(List<DailyAteFood> dailyAteFoods) {
        List<DailyAteFoodDTO> dailyAteFoodDTOS = new ArrayList<>();

        for (DailyAteFood dailyAteFood : dailyAteFoods) {
            dailyAteFoodDTOS.add(convertDailyAteFoodToDailyAteFoodDTO(dailyAteFood));
        }

        return dailyAteFoodDTOS;
    }
}
