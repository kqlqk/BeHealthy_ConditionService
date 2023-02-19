package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class UserPhotoException extends RuntimeException {
    public UserPhotoException(String message) {
        super(message);
    }

    public UserPhotoException(Throwable cause) {
        super(cause);
    }
}
