package me.kqlqk.behealthy.condition_service.repository;

import me.kqlqk.behealthy.condition_service.model.OwnDailyKcals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnDailyKcalsRepository extends JpaRepository<OwnDailyKcals, Long> {
    OwnDailyKcals findByUserId(long userId);
}
