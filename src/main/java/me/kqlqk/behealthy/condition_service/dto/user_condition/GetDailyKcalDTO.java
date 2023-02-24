package me.kqlqk.behealthy.condition_service.dto.user_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.DailyKcal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDailyKcalDTO {
    private int protein;
    private int fat;
    private int carb;

    public static GetDailyKcalDTO convert(DailyKcal dailyKcal) {
        return new GetDailyKcalDTO(dailyKcal.getProtein(), dailyKcal.getFat(), dailyKcal.getCarb());
    }
}
