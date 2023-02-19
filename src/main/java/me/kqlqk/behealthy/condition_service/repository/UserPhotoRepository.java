package me.kqlqk.behealthy.condition_service.repository;

import me.kqlqk.behealthy.condition_service.model.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {
    boolean existsByUserIdAndPhotoDate(long userId, Date photoDate);

    boolean existsByUserId(long userId);

    List<UserPhoto> getByUserId(long userId);

    UserPhoto getByUserIdAndPhotoDate(long userId, Date date);
}
