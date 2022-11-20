package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class UserConditionNotFoundException extends RuntimeException {
    private static final String USER_CONDITION_NOT_FOUND = "UserConditionNotFound";

    public UserConditionNotFoundException(String message) {
        super(USER_CONDITION_NOT_FOUND + " | " + message);
    }
}
