package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.condition_service.model.DailyKcals;
import org.springframework.stereotype.Service;

@Service
public interface DailyKcalsService {
    DailyKcals getById(long id);

    DailyKcals getByUserId(long id);

    DailyKcals generateDailyKcals(UserConditionDTO userConditionDTO);
}
