package me.kqlqk.behealthy.condition_service.dto.daily_ate_food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDailyAteFoodDTO {
    @Pattern(regexp = ".{2,50}", message = "Product name should be between 2 and 50 characters")
    @NotEmpty(message = "Name cannot be null")
    private String name;

    @DecimalMin(value = "0.1", message = "Weight should be > 0")
    @DecimalMax(value = "9999.9", message = "Weight should be < 10000")
    private double weight;

    @Min(value = 0, message = "Protein should be > -1")
    @Max(value = 999, message = "Protein should be < 1000")
    private int protein;

    @Min(value = 0, message = "Fat should be > -1")
    @Max(value = 999, message = "Fat should be < 1000")
    private int fat;

    @Min(value = 0, message = "Carb should be > -1")
    @Max(value = 999, message = "Carb should be < 1000")
    private int carb;
}
