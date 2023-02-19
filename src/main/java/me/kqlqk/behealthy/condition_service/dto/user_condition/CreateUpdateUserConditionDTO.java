package me.kqlqk.behealthy.condition_service.dto.user_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateUserConditionDTO {
    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @Min(value = 15, message = "Age should be between 15 and 60")
    @Max(value = 60, message = "Age should be between 15 and 60")
    private int age;

    @Min(value = 150, message = "Height should be between 150 and 200")
    @Max(value = 200, message = "Height should be between 150 and 200")
    private int height;

    @Min(value = 40, message = "Weight should be between 40 and 140")
    @Max(value = 140, message = "Weight should be between 40 and 140")
    private int weight;

    @NotNull(message = "Intensity cannot be null")
    private Intensity intensity;

    @NotNull(message = "Goal cannot be null")
    private Goal goal;

    @DecimalMin(value = "3.0", message = "FatPercent should be between 3 and 40")
    @DecimalMax(value = "40.0", message = "FatPercent should be between 3 and 40")
    private double fatPercent;

    private boolean fatPercentExists;

    @Min(value = 2, message = "Fat fold between chest and ilium should be between 2 and 50")
    @Max(value = 50, message = "Fat fold between chest and ilium should be between 2 and 50")
    private int fatFoldBetweenChestAndIlium;

    @Min(value = 5, message = "Fat fold between navel and lower belly should be between 5 and 70")
    @Max(value = 70, message = "Fat fold between navel and lower belly should be between 5 and 70")
    private int fatFoldBetweenNavelAndLowerBelly;

    @Min(value = 2, message = "Fat fold between nipple and armpit should be between 2 and 50")
    @Max(value = 50, message = "Fat fold between nipple and armpit should be between 2 and 50")
    private int fatFoldBetweenNippleAndArmpit;

    @Min(value = 2, message = "Fat fold between nipple and upper chest should be between 2 and 50")
    @Max(value = 50, message = "Fat fold between nipple and upper chest should be between 2 and 50")
    private int fatFoldBetweenNippleAndUpperChest;

    @Min(value = 2, message = "Fat fold between shoulder and elbow should be between 2 and 50")
    @Max(value = 50, message = "Fat fold between shoulder and elbow should be between 2 and 50")
    private int fatFoldBetweenShoulderAndElbow;

    public CreateUpdateUserConditionDTO(Gender gender, int age, int height, int weight, Intensity intensity, Goal goal, double fatPercent) {
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.intensity = intensity;
        this.goal = goal;
        this.fatPercent = fatPercent;
        this.fatPercentExists = true;
    }

    public CreateUpdateUserConditionDTO(Gender gender,
                                        int age,
                                        int height,
                                        int weight,
                                        Intensity intensity,
                                        Goal goal,
                                        int fatFoldBetweenChestAndIlium,
                                        int fatFoldBetweenNavelAndLowerBelly,
                                        int fatFoldBetweenNippleAndArmpit,
                                        int fatFoldBetweenNippleAndUpperChest) {
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.intensity = intensity;
        this.goal = goal;
        this.fatPercentExists = false;
        this.fatFoldBetweenChestAndIlium = fatFoldBetweenChestAndIlium;
        this.fatFoldBetweenNavelAndLowerBelly = fatFoldBetweenNavelAndLowerBelly;
        this.fatFoldBetweenNippleAndArmpit = fatFoldBetweenNippleAndArmpit;
        this.fatFoldBetweenNippleAndUpperChest = fatFoldBetweenNippleAndUpperChest;
    }

    public CreateUpdateUserConditionDTO(Gender gender,
                                        int age,
                                        int height,
                                        int weight,
                                        Intensity intensity,
                                        Goal goal,
                                        int fatFoldBetweenShoulderAndElbow,
                                        int fatFoldBetweenChestAndIlium,
                                        int fatFoldBetweenNavelAndLowerBelly) {
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.intensity = intensity;
        this.goal = goal;
        this.fatPercentExists = false;
        this.fatFoldBetweenChestAndIlium = fatFoldBetweenChestAndIlium;
        this.fatFoldBetweenNavelAndLowerBelly = fatFoldBetweenNavelAndLowerBelly;
        this.fatFoldBetweenShoulderAndElbow = fatFoldBetweenShoulderAndElbow;
    }
}
