package me.kqlqk.behealthy.kcals_counter_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.kcals_counter_service.model.UserCondition;
import me.kqlqk.behealthy.kcals_counter_service.model.enums.Gender;
import me.kqlqk.behealthy.kcals_counter_service.model.enums.Goal;
import me.kqlqk.behealthy.kcals_counter_service.model.enums.Intensity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConditionDTO {
    private long id;
    private long userId;
    private Gender gender;
    private byte age;
    private short height;
    private short weight;
    private Intensity intensity;
    private Goal goal;


    public static UserConditionDTO convertUserConditionToUserConditionDTO(UserCondition userCondition) {
        return new UserConditionDTO(
                userCondition.getId(),
                userCondition.getUserId(),
                userCondition.getGender(),
                userCondition.getAge(),
                userCondition.getHeight(),
                userCondition.getWeight(),
                userCondition.getIntensity(),
                userCondition.getGoal());
    }
}
