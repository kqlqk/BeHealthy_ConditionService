package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.model.KcalsInfo;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import org.springframework.stereotype.Service;

@Service
public interface UserConditionService {
    UserCondition getById(long id);

    UserCondition getByUserId(long id);

    KcalsInfo getKcalsInfoByUserId(long id);

    boolean existsByUserId(long id);

    void generateAndSaveCondition(long userId,
                                  Gender gender,
                                  int age,
                                  int height,
                                  int weight,
                                  Intensity intensity,
                                  Goal goal,
                                  double fatPercent);

    void updateCondition(long userId,
                         Gender gender,
                         int age,
                         int height,
                         int weight,
                         Intensity intensity,
                         Goal goal,
                         double fatPercent);
}
