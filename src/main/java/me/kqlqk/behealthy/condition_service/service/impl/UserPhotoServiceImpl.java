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
    private static String USER_PHOTO_DIRECTORY = "BeHealthy_ConditionService/src/main/resources/user_photo/";
    private static final String DELIMITER = "--";
    private static final String PHOTO_FORMAT = ".jpg";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");

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
    public boolean existsByUserIdAndDate(long userId, String dateString) {
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new UserPhotoException(e);
        }

        return existsByUserIdAndDate(userId, date);
    }

    @Override
    public boolean existsByUserIdAndDate(long userId, Date date) {
        return userPhotoRepository.existsByUserIdAndPhotoDate(userId, date);
    }

    @Override
    public UserPhoto getByUserIdAndDate(long userId, String dateString) {
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new UserPhotoException(e);
        }

        return getByUserIdAndDate(userId, date);
    }

    @Override
    public UserPhoto getByUserIdAndDate(long userId, Date date) {
        return userPhotoRepository.getByUserIdAndPhotoDate(userId, date);
    }

    public UserPhotoDTO getDTOWithEncodedPhoto(long userId, String date) {
        UserPhoto userPhoto = getByUserIdAndDate(userId, date);
        UserPhotoDTO userPhotoDTO = UserPhotoDTO.convertUserPhotoToUserPhotoDTO(userPhoto);

        byte[] fileContent;
        try {
            fileContent = FileUtils.readFileToByteArray(new File(userPhoto.getPhotoPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String encodedPhoto = Base64.getEncoder().encodeToString(fileContent);
        userPhotoDTO.setEncodedPhoto(encodedPhoto);

        return userPhotoDTO;
    }

    @Override
    public void savePhoto(UserPhotoDTO userPhotoDTO) {
        Set<ConstraintViolation<UserPhotoDTO>> constraintViolations = validator.validate(userPhotoDTO);

        if (!constraintViolations.isEmpty()) {
            throw new UserPhotoException(constraintViolations.iterator().next().getMessage());
        }

        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setUserId(userPhotoDTO.getUserId());

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
        String photoName = userId + DELIMITER + dateFormat.format(photoDate);
        String path = USER_PHOTO_DIRECTORY + photoName + PHOTO_FORMAT;

        byte[] decodedBytes = Base64.getDecoder().decode(encodedPhoto);
        try {
            FileUtils.writeByteArrayToFile(new File(path), decodedBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return path;
    }

    public void setUserPhotoDirectory(String directory) {
        USER_PHOTO_DIRECTORY = directory;
    }
}
