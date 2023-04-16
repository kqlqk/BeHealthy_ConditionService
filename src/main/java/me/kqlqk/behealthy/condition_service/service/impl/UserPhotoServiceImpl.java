package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserPhotoAlreadyExistsException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserPhotoException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserPhotoNotFoundException;
import me.kqlqk.behealthy.condition_service.model.UserPhoto;
import me.kqlqk.behealthy.condition_service.repository.UserPhotoRepository;
import me.kqlqk.behealthy.condition_service.service.UserPhotoService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserPhotoServiceImpl implements UserPhotoService {
    @Value("${photo.dir}")
    private String USER_PHOTO_DIRECTORY;
    private static final String DELIMITER = "--";
    private static final String PHOTO_FORMAT = ".jpg";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");

    private final UserPhotoRepository userPhotoRepository;

    @Autowired
    public UserPhotoServiceImpl(UserPhotoRepository userPhotoRepository) {
        this.userPhotoRepository = userPhotoRepository;
    }

    @Override
    public UserPhoto getByUserIdAndDate(long userId, String dateString) {
        Date date;
        try {
            date = dateFormat.parse(dateString);
        }
        catch (ParseException e) {
            throw new UserPhotoException(e);
        }

        return getByUserIdAndDate(userId, date);
    }

    @Override
    public UserPhoto getByUserIdAndDate(long userId, Date date) {
        return userPhotoRepository.getByUserIdAndPhotoDate(userId, date)
                .orElseThrow(() -> new UserPhotoNotFoundException("UserPhoto with userId = " + userId + " and date = " + dateFormat.format(date) + " not found"));
    }

    @Override
    public List<UserPhoto> getByUserId(long userId) {
        List<UserPhoto> res = userPhotoRepository.getByUserId(userId)
                .orElseThrow(() -> new UserPhotoNotFoundException("UserPhotos with userId = " + userId + " not found"));

        if (res.isEmpty()) {
            throw new UserPhotoNotFoundException("UserPhotos with userId = " + userId + " not found");
        }

        return res;
    }

    @Override
    public String getEncodedPhoto(long userId, String dateString) {
        Date date;
        try {
            date = dateFormat.parse(dateString);
        }
        catch (ParseException e) {
            throw new UserPhotoException(e);
        }

        return getEncodedPhoto(userId, date);
    }

    @Override
    public String getEncodedPhoto(long userId, Date date) {
        if (!userPhotoRepository.existsByUserIdAndPhotoDate(userId, date)) {
            throw new UserPhotoNotFoundException("UserPhoto with userId = " + userId + " and date = " + dateFormat.format(date) + " not found");
        }

        UserPhoto userPhoto = getByUserIdAndDate(userId, date);

        byte[] fileContent;
        try {
            fileContent = FileUtils.readFileToByteArray(new File(userPhoto.getPhotoPath()));
        }
        catch (IOException e) {
            throw new UserPhotoException(e);
        }

        return Base64.getEncoder().encodeToString(fileContent);
    }

    @Override
    public void save(UserPhoto userPhoto, String encodedPhoto) {
        if (userPhotoRepository.existsByUserIdAndPhotoDate(userPhoto.getUserId(), userPhoto.getPhotoDate())) {
            throw new UserPhotoAlreadyExistsException(
                    "UserPhoto with userId = " + userPhoto.getUserId() + " and photoDate = " + dateFormat.format(userPhoto.getPhotoDate()) + " already exists");
        }

        String path = saveFileAndReturnPath(userPhoto.getUserId(), userPhoto.getPhotoDate(), encodedPhoto);

        userPhoto.setPhotoPath(path);

        userPhotoRepository.save(userPhoto);
    }

    @Override
    public Date convertStringToDate(String dateString) {
        Date date;
        try {
            date = dateFormat.parse(dateString);
        }
        catch (ParseException e) {
            throw new UserPhotoException(e);
        }

        return date;
    }

    @Override
    public void deleteByUserIdAndDate(long userId, String dateString) {
        Date date;
        try {
            date = dateFormat.parse(dateString);
        }
        catch (ParseException e) {
            throw new UserPhotoException(e);
        }

        deleteByUserIdAndDate(userId, date);
    }

    @Override
    public void deleteByUserIdAndDate(long userId, Date date) {
        UserPhoto userPhoto = getByUserIdAndDate(userId, date);

        File file = new File(userPhoto.getPhotoPath());
        if (!file.delete()) {
            log.warn("Photo wasn't deleted, UserPhoto = " + userPhoto);
        }

        userPhotoRepository.delete(userPhoto);
    }

    @Override
    @Transactional
    public void deleteByUserId(long userId) {
        userPhotoRepository.deleteByUserId(userId);
    }

    private String saveFileAndReturnPath(long userId, Date photoDate, String encodedPhoto) {
        String name = userId + DELIMITER + dateFormat.format(photoDate);
        String path = USER_PHOTO_DIRECTORY + "/" + name + PHOTO_FORMAT;

        byte[] decodedBytes = Base64.getDecoder().decode(encodedPhoto);
        try {
            File file = new File(path);
            FileUtils.writeByteArrayToFile(file, decodedBytes);
            file.createNewFile();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return path;
    }

}
