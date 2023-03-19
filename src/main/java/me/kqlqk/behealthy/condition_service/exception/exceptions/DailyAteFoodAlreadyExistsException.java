package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class DailyAteFoodAlreadyExistsException extends RuntimeException {
    public DailyAteFoodAlreadyExistsException(String message) {
        super(message);
    }
}
