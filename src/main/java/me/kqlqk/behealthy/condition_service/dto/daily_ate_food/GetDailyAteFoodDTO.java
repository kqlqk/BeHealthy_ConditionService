package me.kqlqk.behealthy.condition_service.dto.daily_ate_food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.DailyAteFood;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDailyAteFoodDTO {
    private long id;
    private String name;
    private double weight;
    private int kcal;
    private int protein;
    private int fat;
    private int carb;
    private boolean today;

    public static GetDailyAteFoodDTO convert(DailyAteFood dailyAteFood) {
        return new GetDailyAteFoodDTO(dailyAteFood.getId(),
                                      dailyAteFood.getName(),
                                      dailyAteFood.getWeight(),
                                      dailyAteFood.getKcal(),
                                      dailyAteFood.getProtein(),
                                      dailyAteFood.getFat(),
                                      dailyAteFood.getCarb(),
                                      dailyAteFood.isToday());
    }

    public static List<GetDailyAteFoodDTO> convertList(List<DailyAteFood> dailyAteFoods) {
        List<GetDailyAteFoodDTO> res = new ArrayList<>();
        dailyAteFoods.forEach(e -> res.add(convert(e)));

        return res;
    }
}
