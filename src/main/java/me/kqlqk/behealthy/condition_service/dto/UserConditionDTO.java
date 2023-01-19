package me.kqlqk.behealthy.condition_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConditionDTO {
    private long id;

    private long userId;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @Min(value = 15, message = "Age should be between 15 and 60")
    @Max(value = 60, message = "Age should be between 15 and 60")
    private int age;

    @Min(value = 150, message = "Height should be between 150 and 200")
    @Max(value = 200, message = "Height should be between 150 and 200")
    private int height;

    @Min(value = 40, message = "Weight should be between 40 and 90")
    @Max(value = 150, message = "Weight should be between 40 and 90")
    private int weight;

    @NotNull(message = "Intensity cannot be null")
    private Intensity intensity;

    @NotNull(message = "Goal cannot be null")
    private Goal goal;

    @Min(value = 1, message = "FatPercent should be between 1 and 50")
    @Max(value = 40, message = "FatPercent should be between 1 and 40")
    private double fatPercent;


    public UserConditionDTO(long userId, Gender gender, int age, int height, int weight, Intensity intensity, Goal goal, double fatPercent) {
        this.userId = userId;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.intensity = intensity;
        this.goal = goal;
        this.fatPercent = fatPercent;
    }

    public static UserConditionDTO convertUserConditionToUserConditionDTO(UserCondition userCondition) {
        return new UserConditionDTO(
                userCondition.getId(),
                userCondition.getUserId(),
                userCondition.getGender(),
                userCondition.getAge(),
                userCondition.getHeight(),
                userCondition.getWeight(),
                userCondition.getIntensity(),
                userCondition.getGoal(),
                userCondition.getFatPercent());
    }
}
