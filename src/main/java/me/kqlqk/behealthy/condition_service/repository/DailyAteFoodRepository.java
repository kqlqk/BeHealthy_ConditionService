package me.kqlqk.behealthy.condition_service.repository;

import me.kqlqk.behealthy.condition_service.model.DailyAteFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyAteFoodRepository extends JpaRepository<DailyAteFood, Long> {
    List<DailyAteFood> findByUserIdOrderByIdAsc(long userId);

    boolean existsByUserId(long userId);
}
