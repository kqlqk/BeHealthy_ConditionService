package me.kqlqk.behealthy.condition_service.dto.user_kcal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUpdateUserKcalDTO {
    @Min(value = 1, message = "Kcal should be between 1 and 9999")
    @Max(value = 9999, message = "Kcal should be between 1 and 9999")
    private int kcal;

    @Min(value = 0, message = "Protein should be between 0 and 999")
    @Max(value = 999, message = "Protein should be between 0 and 999")
    private int protein;

    @Min(value = 0, message = "Fat should be between 0 and 999")
    @Max(value = 999, message = "Fat should be between 0 and 999")
    private int fat;

    @Min(value = 0, message = "Carb should be between 0 and 999")
    @Max(value = 999, message = "Carb should be between 0 and 999")
    private int carb;

    private boolean inPriority;
    private boolean onlyKcal;
}
