package me.kqlqk.behealthy.kcal_counter_service.repository;

import me.kqlqk.behealthy.kcal_counter_service.model.DailyFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyFoodRepository extends JpaRepository<DailyFood, Long> {
    DailyFood findById(long id);

    List<DailyFood> findByUserId(long userId);
}
