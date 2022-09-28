package me.kqlqk.behealthy.kcal_counter_service.service.impl;

import me.kqlqk.behealthy.kcal_counter_service.exception.exceptions.UserConditionAlreadyExistsException;
import me.kqlqk.behealthy.kcal_counter_service.model.KcalsInfo;
import me.kqlqk.behealthy.kcal_counter_service.model.UserCondition;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Gender;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Goal;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Intensity;
import me.kqlqk.behealthy.kcal_counter_service.repository.UserConditionRepository;
import me.kqlqk.behealthy.kcal_counter_service.service.KcalsInfoService;
import me.kqlqk.behealthy.kcal_counter_service.service.UserConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConditionServiceImpl implements UserConditionService {
    private final UserConditionRepository userConditionRepository;
    private final KcalsInfoService kcalsInfoService;

    @Autowired
    public UserConditionServiceImpl(UserConditionRepository userConditionRepository, KcalsInfoService kcalsInfoService) {
        this.userConditionRepository = userConditionRepository;
        this.kcalsInfoService = kcalsInfoService;
    }

    @Override
    public UserCondition findById(long id) {
        return userConditionRepository.findById(id);
    }

    @Override
    public UserCondition findByUserId(long id) {
        return userConditionRepository.findByUserId(id);
    }

    @Override
    public boolean existsByUserId(long id) {
        return userConditionRepository.existsByUserId(id);
    }

    @Override
    public void generateAndSaveCondition(long userId,
                                         Gender gender,
                                         byte age,
                                         short height,
                                         short weight,
                                         Intensity intensity,
                                         Goal goal) {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId cannot be <= 0");
        }
        if (gender == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        }
        if (age <= 10) {
            throw new IllegalArgumentException("Age cannot be <= 10");
        }
        if (height <= 130) {
            throw new IllegalArgumentException("Height cannot be <= 130");
        }
        if (weight <= 30) {
            throw new IllegalArgumentException("Weight cannot be <= 30");
        }
        if (intensity == null) {
            throw new IllegalArgumentException("Intensity cannot be null");
        }
        if (goal == null) {
            throw new IllegalArgumentException("Goal cannot be null");
        }
        if (existsByUserId(userId)) {
            throw new UserConditionAlreadyExistsException("User condition with userId = " + userId + " already exists");
        }

        KcalsInfo kcalsInfo = kcalsInfoService.generateDailyKcals(
                gender, age, height, weight, intensity, goal);

        UserCondition userCondition = new UserCondition(userId, kcalsInfo, gender, age, height, weight, intensity, goal);

        userConditionRepository.save(userCondition);
    }
}
