package me.kqlqk.behealthy.condition_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserConditionWithoutFatPercentMaleDTO {
    private long userId;

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

    @Min(value = 2, message = "Fat fold between chest and ilium should be between 2 and 50")
    @Max(value = 50, message = "Fat fold between chest and ilium should be between 2 and 50")
    int fatFoldBetweenChestAndIlium;

    @Min(value = 5, message = "Fat fold between navel and lower belly should be between 5 and 70")
    @Max(value = 70, message = "Fat fold between navel and lower belly should be between 5 and 70")
    int fatFoldBetweenNavelAndLowerBelly;

    @Min(value = 2, message = "Fat fold between nipple and armpit should be between 2 and 50")
    @Max(value = 50, message = "Fat fold between nipple and armpit should be between 2 and 50")
    int fatFoldBetweenNippleAndArmpit;

    @Min(value = 2, message = "Fat fold between nipple and upper chest should be between 2 and 50")
    @Max(value = 50, message = "Fat fold between nipple and upper chest should be between 2 and 50")
    int fatFoldBetweenNippleAndUpperChest;
}
