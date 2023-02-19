package me.kqlqk.behealthy.condition_service.repository;

import me.kqlqk.behealthy.condition_service.model.UserKcal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserKcalRepository extends JpaRepository<UserKcal, Long> {
    UserKcal findByUserId(long userId);

    boolean existsByUserId(long userId);
}
