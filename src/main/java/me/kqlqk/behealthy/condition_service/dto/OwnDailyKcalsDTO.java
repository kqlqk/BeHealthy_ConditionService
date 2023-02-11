package me.kqlqk.behealthy.condition_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnDailyKcalsDTO {
    private long id;
    private int protein;
    private int fat;
    private int carb;
    private long userId;

    public OwnDailyKcalsDTO(int protein, int fat, int carb, long userId) {
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
        this.userId = userId;
    }
}
