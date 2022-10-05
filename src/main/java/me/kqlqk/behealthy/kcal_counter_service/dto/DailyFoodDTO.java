package me.kqlqk.behealthy.kcal_counter_service.dto;

import lombok.Data;

@Data
public class DailyFoodDTO {
    private long id;
    private long userId;
    private String name;
    private double weight;
    private double kcals;
    private double proteins;
    private double fats;
    private double carbs;
}
