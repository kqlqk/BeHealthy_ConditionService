package me.kqlqk.behealthy.condition_service.exception.exceptions;

public class UserPhotoAlreadyExistsException extends RuntimeException {
    private static final String USER_PHOTO_ALREADY_EXISTS_EXCEPTION = "UserPhotoAlreadyExists";

    public UserPhotoAlreadyExistsException(String message) {
        super(USER_PHOTO_ALREADY_EXISTS_EXCEPTION + " | " + message);
    }
}
