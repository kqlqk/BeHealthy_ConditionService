package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class FoodException extends RuntimeException {
    private static final String FOOD_EXCEPTION = "Food";

    public FoodException(String message) {
        super(FOOD_EXCEPTION + " | " + message);
    }
}
