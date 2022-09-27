package me.kqlqk.behealthy.kcal_counter_service.exception.exceptions;

public class UserConditionAlreadyExistsException extends RuntimeException {
    public UserConditionAlreadyExistsException(String message) {
        super(message);
    }
}
