package me.kqlqk.behealthy.condition_service.dto.user_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserConditionDTO {
    private GetDailyKcalDTO dailyKcalDTO;
    private Gender gender;
    private int age;
    private int height;
    private int weight;
    private Intensity intensity;
    private Goal goal;
    private double fatPercent;

    public static GetUserConditionDTO convert(UserCondition userCondition) {
        return new GetUserConditionDTO(GetDailyKcalDTO.convert(userCondition.getDailyKcal()),
                                       userCondition.getGender(),
                                       userCondition.getAge(),
                                       userCondition.getHeight(),
                                       userCondition.getWeight(),
                                       userCondition.getIntensity(),
                                       userCondition.getGoal(),
                                       userCondition.getFatPercent());
    }
}
