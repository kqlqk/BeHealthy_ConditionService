package me.kqlqk.behealthy.condition_service.repository;

import me.kqlqk.behealthy.condition_service.model.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {
    boolean existsByPhotoPath(String photoPath);
}
