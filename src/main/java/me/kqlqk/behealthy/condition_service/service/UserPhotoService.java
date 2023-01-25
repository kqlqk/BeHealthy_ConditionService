package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.dto.UserPhotoDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserPhotoService {
    boolean existsByPhotoPath(String photoPath);

    void savePhoto(UserPhotoDTO userPhotoDTO);
}
