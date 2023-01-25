package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class UserPhotoException extends RuntimeException {
    private static final String USER_PHOTO_EXCEPTION = "UserPhoto";

    public UserPhotoException(String message) {
        super(USER_PHOTO_EXCEPTION + " | " + message);
    }

    public UserPhotoException(Throwable cause) {
        super(cause);
    }
}
