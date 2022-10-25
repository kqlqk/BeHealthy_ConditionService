package me.kqlqk.behealthy.kcals_counter_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.kcals_counter_service.exception.exceptions.UserConditionAlreadyExistsException;
import me.kqlqk.behealthy.kcals_counter_service.exception.exceptions.UserConditionNotFound;
import me.kqlqk.behealthy.kcals_counter_service.model.KcalsInfo;
import me.kqlqk.behealthy.kcals_counter_service.model.UserCondition;
import me.kqlqk.behealthy.kcals_counter_service.model.enums.Gender;
import me.kqlqk.behealthy.kcals_counter_service.model.enums.Goal;
import me.kqlqk.behealthy.kcals_counter_service.model.enums.Intensity;
import me.kqlqk.behealthy.kcals_counter_service.repository.UserConditionRepository;
import me.kqlqk.behealthy.kcals_counter_service.service.KcalsInfoService;
import me.kqlqk.behealthy.kcals_counter_service.service.UserConditionService;
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
    public UserCondition getById(long id) {
        return userConditionRepository.findById(id);
    }

    @Override
    public UserCondition getByUserId(long id) {
        return userConditionRepository.findByUserId(id);
    }

    @Override
    public KcalsInfo getKcalsInfoByUserId(long id) {
        if (!existsByUserId(id)) {
            throw new UserConditionNotFound("User condition with userId = " + id + " not found");
        }

        UserCondition userCondition = getByUserId(id);

        return userCondition.getKcalsInfo();
    }

    @Override
    public boolean existsByUserId(long id) {
        return userConditionRepository.existsByUserId(id);
    }

    @Override
    public void generateAndSaveCondition(long userId,
                                         @NonNull Gender gender,
                                         byte age,
                                         short height,
                                         short weight,
                                         @NonNull Intensity intensity,
                                         @NonNull Goal goal) {
        if (userId <= 0) {
            throw new IllegalArgumentException("UserId cannot be <= 0");
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
        if (existsByUserId(userId)) {
            throw new UserConditionAlreadyExistsException("User condition with userId = " + userId + " already exists");
        }

        KcalsInfo kcalsInfo = kcalsInfoService.generateDailyKcals(
                gender, age, height, weight, intensity, goal);

        UserCondition userCondition = new UserCondition(userId, kcalsInfo, gender, age, height, weight, intensity, goal);

        userConditionRepository.save(userCondition);
    }

    @Override
    public void updateCondition(long userId,
                                @NonNull Gender gender,
                                byte age,
                                short height,
                                short weight,
                                @NonNull Intensity intensity,
                                @NonNull Goal goal) {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId cannot be <= 0");
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
        if (!existsByUserId(userId)) {
            throw new UserConditionNotFound("User condition with userId = " + userId + " not found");
        }

        UserCondition userCondition = getByUserId(userId);
        KcalsInfo kcalsInfo = userCondition.getKcalsInfo();
        KcalsInfo updatedKcalsInfo = kcalsInfoService.generateDailyKcals(gender, age, height, weight, intensity, goal);

        kcalsInfo.setProtein(updatedKcalsInfo.getProtein());
        kcalsInfo.setFat(updatedKcalsInfo.getFat());
        kcalsInfo.setCarb(updatedKcalsInfo.getCarb());

        userCondition.setGender(gender);
        userCondition.setAge(age);
        userCondition.setHeight(height);
        userCondition.setWeight(weight);
        userCondition.setIntensity(intensity);
        userCondition.setGoal(goal);

        userConditionRepository.save(userCondition);
    }
}
