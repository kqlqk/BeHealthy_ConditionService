package me.kqlqk.behealthy.condition_service.repository;

import me.kqlqk.behealthy.condition_service.model.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {
    boolean existsByUserIdAndPhotoDate(long userId, Date photoDate);

    Optional<List<UserPhoto>> getByUserId(long userId);

    Optional<UserPhoto> getByUserIdAndPhotoDate(long userId, Date date);

    void deleteByUserId(long userId);
}
