package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.model.KcalsInfo;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import org.springframework.stereotype.Service;

@Service
public interface KcalsInfoService {
    KcalsInfo getById(long id);

    KcalsInfo generateDailyKcals(Gender gender,
                                 int age,
                                 int height,
                                 int weight,
                                 Intensity intensity,
                                 Goal goal,
                                 double fatPercent);
}
