package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class UserConditionException extends RuntimeException {
    private static final String USER_CONDITION_EXCEPTION = "UserCondition";

    public UserConditionException(String message) {
        super(USER_CONDITION_EXCEPTION + " | " + message);
    }
}
