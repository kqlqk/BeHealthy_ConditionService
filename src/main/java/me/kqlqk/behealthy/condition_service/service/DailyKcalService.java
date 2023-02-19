package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.model.DailyKcal;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import org.springframework.stereotype.Service;

@Service
public interface DailyKcalService {
    DailyKcal generateDailyKcals(int weight, double fatPercent, Intensity intensity, Goal goal);
}
