package me.kqlqk.behealthy.kcals_counter_service.repository;

import me.kqlqk.behealthy.kcals_counter_service.model.DailyFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyFoodRepository extends JpaRepository<DailyFood, Long> {
    DailyFood findById(long id);

    List<DailyFood> findByUserId(long userId);
}
