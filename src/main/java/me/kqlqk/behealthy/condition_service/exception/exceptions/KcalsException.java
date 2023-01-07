package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class KcalsException extends RuntimeException {
    private static final String KCALS_EXCEPTION = "Kcals";

    public KcalsException(String message) {
        super(KCALS_EXCEPTION + " | " + message);
    }
}
