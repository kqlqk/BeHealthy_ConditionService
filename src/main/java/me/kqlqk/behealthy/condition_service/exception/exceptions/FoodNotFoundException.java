package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class FoodNotFoundException extends RuntimeException {
    private static final String FOOD_NOT_FOUND = "FoodNotFound";

    public FoodNotFoundException(String message) {
        super(FOOD_NOT_FOUND + " | " + message);
    }
}
