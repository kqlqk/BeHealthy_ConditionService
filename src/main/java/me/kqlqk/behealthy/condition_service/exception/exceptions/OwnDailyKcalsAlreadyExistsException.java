package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class OwnDailyKcalsAlreadyExistsException extends RuntimeException {
    private static final String OWN_DAILY_KCALS_ALREADY_EXISTS = "OwnDailyKcalsAlreadyExists";

    public OwnDailyKcalsAlreadyExistsException(String message) {
        super(OWN_DAILY_KCALS_ALREADY_EXISTS + " | " + message);
    }
}
