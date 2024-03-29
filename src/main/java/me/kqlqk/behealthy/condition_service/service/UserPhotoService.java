package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.model.UserPhoto;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface UserPhotoService {
    UserPhoto getByUserIdAndDate(long userId, String dateString);

    UserPhoto getByUserIdAndDate(long userId, Date date);

    List<UserPhoto> getByUserId(long userId);

    String getEncodedPhoto(long userId, String dateString);

    String getEncodedPhoto(long userId, Date date);

    void save(UserPhoto userPhoto, String encodedPhoto);

    Date convertStringToDate(String dateString);

    void deleteByUserIdAndDate(long userId, String date);

    void deleteByUserIdAndDate(long userId, Date date);

    void deleteByUserId(long userId);
}
