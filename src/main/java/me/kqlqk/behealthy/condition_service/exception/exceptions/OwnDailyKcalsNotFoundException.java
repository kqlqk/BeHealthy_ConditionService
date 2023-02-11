package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class OwnDailyKcalsNotFoundException extends RuntimeException {
    private static final String OWN_DAILY_KCALS_NOT_FOUND = "OwnDailyKcalsNotFound";

    public OwnDailyKcalsNotFoundException(String message) {
        super(OWN_DAILY_KCALS_NOT_FOUND + " | " + message);
    }
}
