package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class DailyKcalsNotFoundException extends RuntimeException {
    private static final String DAILY_KCALS_NOT_FOUND = "DailyKcalsNotFound";

    public DailyKcalsNotFoundException(String message) {
        super(DAILY_KCALS_NOT_FOUND + " | " + message);
    }
}
