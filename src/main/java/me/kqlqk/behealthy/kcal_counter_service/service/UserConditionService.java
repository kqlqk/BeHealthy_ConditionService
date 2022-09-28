package me.kqlqk.behealthy.kcal_counter_service.service;

import me.kqlqk.behealthy.kcal_counter_service.model.enums.Gender;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Goal;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Intensity;
import me.kqlqk.behealthy.kcal_counter_service.model.UserCondition;
import org.springframework.stereotype.Service;

@Service
public interface UserConditionService {
    UserCondition findById(long id);

    UserCondition findByUserId(long id);

    boolean existsByUserId(long id);

    void generateAndSaveCondition(long userId,
                                  Gender gender,
                                  byte age,
                                  short height,
                                  short weight,
                                  Intensity intensity,
                                  Goal goal);
}
