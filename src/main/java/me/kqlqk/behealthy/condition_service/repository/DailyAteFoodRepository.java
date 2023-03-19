package me.kqlqk.behealthy.condition_service.repository;

import me.kqlqk.behealthy.condition_service.model.DailyAteFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DailyAteFoodRepository extends JpaRepository<DailyAteFood, Long> {
    Optional<List<DailyAteFood>> findByUserIdOrderByIdAsc(long userId);

    Optional<DailyAteFood> findByNameAndUserId(String name, long userId);

    boolean existsByNameAndUserId(String name, long userId);
}
