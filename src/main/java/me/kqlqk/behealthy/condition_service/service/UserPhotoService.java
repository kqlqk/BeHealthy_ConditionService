package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.dto.UserPhotoDTO;
import me.kqlqk.behealthy.condition_service.model.UserPhoto;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface UserPhotoService {
    boolean existsByPhotoPath(String photoPath);

    boolean existsByUserIdAndDate(long userId, String dateString);

    boolean existsByUserIdAndDate(long userId, Date date);

    UserPhoto getByUserIdAndDate(long userId, String dateString);

    UserPhoto getByUserIdAndDate(long userId, Date date);

    UserPhotoDTO getDTOWithEncodedPhoto(long userId, String date);

    void savePhoto(UserPhotoDTO userPhotoDTO);
}
