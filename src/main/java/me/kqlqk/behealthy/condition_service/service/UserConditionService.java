package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.condition_service.dto.UserConditionWithoutFatPercentFemaleDTO;
import me.kqlqk.behealthy.condition_service.dto.UserConditionWithoutFatPercentMaleDTO;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import org.springframework.stereotype.Service;

@Service
public interface UserConditionService {
    UserCondition getByUserId(long id);

    boolean existsByUserId(long id);

    void generateAndSaveConditionWithoutFatPercent(UserConditionWithoutFatPercentMaleDTO userConditionWithoutFatPercentMaleDTO,
                                                   UserConditionWithoutFatPercentFemaleDTO userConditionWithoutFatPercentFemaleDTO);

    void generateAndSaveCondition(UserConditionDTO userConditionDTO);

    void updateCondition(UserConditionDTO userConditionDTO);
}
