package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.dto.DailyKcalsDTO;
import me.kqlqk.behealthy.condition_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.condition_service.model.DailyKcals;
import org.springframework.stereotype.Service;

@Service
public interface DailyKcalsService {
    DailyKcals getByUserId(long id);

    DailyKcals generateDailyKcals(UserConditionDTO userConditionDTO);

    void updateDailyKcals(long userId, DailyKcalsDTO dailyKcalsDTO);
}
