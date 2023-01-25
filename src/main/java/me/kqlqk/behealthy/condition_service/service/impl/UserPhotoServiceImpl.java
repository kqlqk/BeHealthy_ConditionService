package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.condition_service.dto.UserPhotoDTO;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserPhotoException;
import me.kqlqk.behealthy.condition_service.model.UserPhoto;
import me.kqlqk.behealthy.condition_service.repository.UserPhotoRepository;
import me.kqlqk.behealthy.condition_service.service.UserPhotoService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Service
public class UserPhotoServiceImpl implements UserPhotoService {
    private static final String USER_PHOTO_DIRECTORY = "C:\\Users\\kqlqk\\Desktop\\BeHealthy_project\\BeHealthy_ConditionService\\src\\main\\resources\\user_photo\\";

    private final UserPhotoRepository userPhotoRepository;
    private final Validator validator;

    @Autowired
    public UserPhotoServiceImpl(UserPhotoRepository userPhotoRepository, Validator validator) {
        this.userPhotoRepository = userPhotoRepository;
        this.validator = validator;
    }

    @Override
    public boolean existsByPhotoPath(@NonNull String photoPath) {
        return userPhotoRepository.existsByPhotoPath(photoPath);
    }

    @Override
    public void savePhoto(UserPhotoDTO userPhotoDTO) {
        Set<ConstraintViolation<UserPhotoDTO>> constraintViolations = validator.validate(userPhotoDTO);

        if (!constraintViolations.isEmpty()) {
            throw new UserPhotoException(constraintViolations.iterator().next().getMessage());
        }

        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setUserId(userPhotoDTO.getUserId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        Date date;
        try {
            date = dateFormat.parse(userPhotoDTO.getPhotoDate());
        } catch (ParseException e) {
            throw new UserPhotoException(e);
        }

        String path = saveFileAndReturnPath(userPhotoDTO.getUserId(), date, userPhotoDTO.getEncodedPhoto());

        if (existsByPhotoPath(path)) {
            return;
        }

        userPhoto.setPhotoPath(path);
        userPhoto.setPhotoDate(date);

        userPhotoRepository.save(userPhoto);
    }

    private String saveFileAndReturnPath(long userId, Date photoDate, String encodedPhoto) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");

        String photoName = userId + "--" + dateFormat.format(photoDate);
        String path = USER_PHOTO_DIRECTORY + photoName + ".jpg";

        byte[] decodedBytes = Base64.getDecoder().decode(encodedPhoto);
        try {
            FileUtils.writeByteArrayToFile(new File(path), decodedBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return path;
    }
}
