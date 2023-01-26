package me.kqlqk.behealthy.condition_service.repository;

import me.kqlqk.behealthy.condition_service.model.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {
    boolean existsByPhotoPath(String photoPath);

    boolean existsByUserIdAndPhotoDate(long userId, Date photoDate);

    UserPhoto getByUserIdAndPhotoDate(long userId, Date date);
}
