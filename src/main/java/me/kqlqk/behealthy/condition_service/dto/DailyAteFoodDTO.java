package me.kqlqk.behealthy.condition_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.DailyAteFood;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyAteFoodDTO {
    private long id;

    private long userId;

    @Pattern(regexp = ".{2,50}", message = "Product name should be between 2 and 50 characters")
    private String name;

    @DecimalMin(value = "0.1", message = "Weight should be > 0")
    @DecimalMax(value = "9999.9", message = "Weight should be < 10000")
    private double weight;

    @DecimalMin(value = "0.0", message = "Kcals should be > 0")
    @DecimalMax(value = "999.9", message = "Kcals should be < 1000")
    private double kcals;

    @DecimalMin(value = "0.0", message = "Proteins should be > 0")
    @DecimalMax(value = "999.9", message = "Proteins should be < 1000")
    private double proteins;

    @DecimalMin(value = "0.0", message = "Fats should be > 0")
    @DecimalMax(value = "999.9", message = "Fats should be < 1000")
    private double fats;

    @DecimalMin(value = "0.0", message = "Carbs should be > 0")
    @DecimalMax(value = "999.9", message = "Carbs should be < 1000")
    private double carbs;

    public DailyAteFoodDTO(long userId, String name, double weight, double kcals, double proteins, double fats, double carbs) {
        this.userId = userId;
        this.name = name;
        this.weight = weight;
        this.kcals = kcals;
        this.proteins = proteins;
        this.fats = fats;
        this.carbs = carbs;
    }

    public static DailyAteFoodDTO convertDailyAteFoodToDailyAteFoodDTO(DailyAteFood dailyAteFood) {
        return new DailyAteFoodDTO(
                dailyAteFood.getId(),
                dailyAteFood.getUserId(),
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
