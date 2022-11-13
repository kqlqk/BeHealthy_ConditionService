package me.kqlqk.behealthy.condition_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.DailyFood;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyFoodDTO {
    private long id;
    private String name;
    private double weight;
    private double kcals;
    private double proteins;
    private double fats;
    private double carbs;

    public static DailyFoodDTO convertDailyFoodToDailyFoodDTO(DailyFood dailyFood) {
        return new DailyFoodDTO(
                dailyFood.getId(),
                dailyFood.getName(),
                dailyFood.getWeight(),
                dailyFood.getKcals(),
                dailyFood.getProteins(),
                dailyFood.getFats(),
                dailyFood.getCarbs()
        );
    }

    public static List<DailyFoodDTO> convertListOfDailyFoodToListOFDailyFoodDTO(List<DailyFood> dailyFoods) {
        List<DailyFoodDTO> dailyFoodDTOs = new ArrayList<>();

        for (DailyFood dailyFood : dailyFoods) {
            dailyFoodDTOs.add(convertDailyFoodToDailyFoodDTO(dailyFood));
        }

        return dailyFoodDTOs;
    }
}
