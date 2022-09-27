package me.kqlqk.behealthy.kcal_counter_service.service;

import me.kqlqk.behealthy.kcal_counter_service.model.Intensity;
import me.kqlqk.behealthy.kcal_counter_service.model.KcalsInfo;
import me.kqlqk.behealthy.kcal_counter_service.model.UserCondition;
import org.springframework.stereotype.Service;

@Service
public interface UserConditionService {
    UserCondition findById(long id);

    UserCondition findByUserId(long id);

    boolean existsByUserId(long id);

    void createCondition(KcalsInfo kcalsInfo, short weight, Intensity intensity, long userId);
}
