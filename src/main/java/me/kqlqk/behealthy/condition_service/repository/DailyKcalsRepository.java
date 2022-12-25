package me.kqlqk.behealthy.condition_service.repository;

import me.kqlqk.behealthy.condition_service.model.DailyKcals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyKcalsRepository extends JpaRepository<DailyKcals, Long> {
    DailyKcals findById(long id);
}
