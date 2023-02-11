package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.dto.OwnDailyKcalsDTO;
import me.kqlqk.behealthy.condition_service.model.OwnDailyKcals;
import org.springframework.stereotype.Service;

@Service
public interface OwnDailyKcalsService {
    OwnDailyKcals getByUserId(long userId);

    void save(OwnDailyKcalsDTO ownDailyKcalsDTO);

    void update(OwnDailyKcalsDTO ownDailyKcalsDTO);
}
