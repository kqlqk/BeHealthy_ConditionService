package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class UserConditionAlreadyExistsException extends RuntimeException {
    public UserConditionAlreadyExistsException(String message) {
        super(message);
    }
}
