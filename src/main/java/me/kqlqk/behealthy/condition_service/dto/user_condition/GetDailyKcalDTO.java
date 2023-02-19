package me.kqlqk.behealthy.condition_service.dto.user_condition;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("dailyKcal")
public class GetDailyKcalDTO {
    private int protein;
    private int fat;
    private int carb;
}
