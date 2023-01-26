package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class UserPhotoNotFoundException extends RuntimeException {
    private static final String USER_PHOTO_NOT_FOUND_EXCEPTION = "UserPhotoNotFound";

    public UserPhotoNotFoundException(String message) {
        super(USER_PHOTO_NOT_FOUND_EXCEPTION + " | " + message);
    }
}
