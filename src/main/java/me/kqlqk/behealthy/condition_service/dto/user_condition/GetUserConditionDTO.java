package me.kqlqk.behealthy.condition_service.dto.user_condition;

import lombok.Data;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;

@Data
public class GetUserConditionDTO {
    private GetDailyKcalDTO getDailyKcalDTO;
    private Gender gender;
    private int age;
    private int height;
    private int weight;
    private Intensity intensity;
    private Goal goal;
    private double fatPercent;
}
