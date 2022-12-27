package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionAlreadyExistsException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionNotFoundException;
import me.kqlqk.behealthy.condition_service.model.DailyKcals;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import me.kqlqk.behealthy.condition_service.repository.UserConditionRepository;
import me.kqlqk.behealthy.condition_service.service.DailyKcalsService;
import me.kqlqk.behealthy.condition_service.service.UserConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UserConditionServiceImpl implements UserConditionService {
    private final UserConditionRepository userConditionRepository;
    private DailyKcalsService dailyKcalsService;

    @Autowired
    public UserConditionServiceImpl(UserConditionRepository userConditionRepository) {
        this.userConditionRepository = userConditionRepository;
    }

    @Autowired
    @Lazy
    public void setDailyKcalsService(DailyKcalsService dailyKcalsService) {
        this.dailyKcalsService = dailyKcalsService;
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
    public boolean existsByUserId(long id) {
        return userConditionRepository.existsByUserId(id);
    }

    @Override
    public void generateAndSaveConditionWithoutFatPercentForMale(long userId,
                                                                 int age,
                                                                 int height,
                                                                 int weight,
                                                                 @NonNull Intensity intensity,
                                                                 @NonNull Goal goal,
                                                                 int fatFoldBetweenChestAndIlium,
                                                                 int fatFoldBetweenNavelAndLowerBelly,
                                                                 int fatFoldBetweenNippleAndArmpit,
                                                                 int fatFoldBetweenNippleAndUpperChest) {
        if (userId <= 0) {
            throw new IllegalArgumentException("UserId cannot be <= 0");
        }
        if (age < 15 || age > 60) {
            throw new IllegalArgumentException("Age should be between 15 and 60");
        }
        if (height < 150 || height > 200) {
            throw new IllegalArgumentException("Height should be between 150 and 200");
        }
        if (weight < 40 || weight > 150) {
            throw new IllegalArgumentException("Weight should be between 40 and 150");
        }
        if (fatFoldBetweenChestAndIlium < 2 || fatFoldBetweenChestAndIlium > 50) {
            throw new IllegalArgumentException("Fat fold between chest and ilium should be between 2 and 50");
        }
        if (fatFoldBetweenNavelAndLowerBelly < 5 || fatFoldBetweenNavelAndLowerBelly > 70) {
            throw new IllegalArgumentException("Fat fold between navel and lower belly should be between 5 and 70");
        }
        if (fatFoldBetweenNippleAndArmpit < 2 || fatFoldBetweenNippleAndArmpit > 50) {
            throw new IllegalArgumentException("Fat fold between nipple and armpit should be between 2 and 50");
        }
        if (fatFoldBetweenNippleAndUpperChest < 2 || fatFoldBetweenNippleAndUpperChest > 50) {
            throw new IllegalArgumentException("Fat fold between nipple and upper chest should be between 2 and 50");
        }
        if (existsByUserId(userId)) {
            throw new UserConditionAlreadyExistsException("User condition with userId = " + userId + " already exists");
        }

        generateAndSaveCondition(userId, Gender.MALE, age, height, weight, intensity, goal, getFatPercent(
                Gender.MALE,
                fatFoldBetweenChestAndIlium,
                fatFoldBetweenNavelAndLowerBelly,
                fatFoldBetweenNippleAndArmpit,
                fatFoldBetweenNippleAndUpperChest,
                0,
                age));
    }

    @Override
    public void generateAndSaveConditionWithoutFatPercentForFemale(long userId,
                                                                   int age,
                                                                   int height,
                                                                   int weight,
                                                                   @NonNull Intensity intensity,
                                                                   @NonNull Goal goal,
                                                                   int fatFoldBetweenShoulderAndElbow,
                                                                   int fatFoldBetweenChestAndIlium,
                                                                   int fatFoldBetweenNavelAndLowerBelly) {
        if (userId <= 0) {
            throw new IllegalArgumentException("UserId cannot be <= 0");
        }
        if (age < 15 || age > 60) {
            throw new IllegalArgumentException("Age should be between 15 and 60");
        }
        if (height < 150 || height > 200) {
            throw new IllegalArgumentException("Height should be between 150 and 200");
        }
        if (weight < 40 || weight > 150) {
            throw new IllegalArgumentException("Weight should be between 40 and 150");
        }
        if (fatFoldBetweenShoulderAndElbow < 2 || fatFoldBetweenShoulderAndElbow > 50) {
            throw new IllegalArgumentException("Fat fold between shoulder and elbow should be between 5 and 50");
        }
        if (fatFoldBetweenChestAndIlium < 2 || fatFoldBetweenChestAndIlium > 50) {
            throw new IllegalArgumentException("Fat fold between chest and ilium should be between 5 and 50");
        }
        if (fatFoldBetweenNavelAndLowerBelly < 5 || fatFoldBetweenNavelAndLowerBelly > 70) {
            throw new IllegalArgumentException("Fat fold between navel and lower belly should be between 5 and 70");
        }
        if (existsByUserId(userId)) {
            throw new UserConditionAlreadyExistsException("User condition with userId = " + userId + " already exists");
        }

        generateAndSaveCondition(userId, Gender.FEMALE, age, height, weight, intensity, goal, getFatPercent(
                Gender.FEMALE,
                fatFoldBetweenChestAndIlium,
                fatFoldBetweenNavelAndLowerBelly,
                0,
                0,
                fatFoldBetweenShoulderAndElbow,
                age));
    }

    protected double getFatPercent(Gender gender,
                                   int fatFoldBetweenChestAndIlium,
                                   int fatFoldBetweenNavelAndLowerBelly,
                                   int fatFoldBetweenNippleAndArmpit,
                                   int fatFoldBetweenNippleAndUpperChest,
                                   int fatFoldBetweenShoulderAndElbow,
                                   int age) {
        switch (gender) {
            case MALE:
                return (
                        (0.27784 * (fatFoldBetweenChestAndIlium + fatFoldBetweenNavelAndLowerBelly + fatFoldBetweenNippleAndArmpit + fatFoldBetweenNippleAndUpperChest)) -
                                (0.00053 * (Math.pow(fatFoldBetweenChestAndIlium, 2) + Math.pow(fatFoldBetweenNavelAndLowerBelly, 2) + Math.pow(fatFoldBetweenNippleAndArmpit, 2) + Math.pow(fatFoldBetweenNippleAndUpperChest, 2))) +
                                (0.12437 * age)) - 3.28791;

            case FEMALE:
                return (
                        (0.41563 * (fatFoldBetweenShoulderAndElbow + fatFoldBetweenChestAndIlium + fatFoldBetweenNavelAndLowerBelly)) -
                                (0.00112 * (Math.pow(fatFoldBetweenShoulderAndElbow, 2) + Math.pow(fatFoldBetweenChestAndIlium, 2) + Math.pow(fatFoldBetweenNavelAndLowerBelly, 2))) +
                                (0.03661 * age)) - 4.03653;
        }

        return 0;
    }

    @Override
    public void generateAndSaveCondition(long userId,
                                         @NonNull Gender gender,
                                         int age,
                                         int height,
                                         int weight,
                                         @NonNull Intensity intensity,
                                         @NonNull Goal goal,
                                         double fatPercent) {
        if (userId <= 0) {
            throw new IllegalArgumentException("UserId cannot be <= 0");
        }
        if (age < 15 || age > 60) {
            throw new IllegalArgumentException("Age should be between 15 and 60");
        }
        if (height < 150 || height > 200) {
            throw new IllegalArgumentException("Height should be between 150 and 200");
        }
        if (weight < 40 || weight > 150) {
            throw new IllegalArgumentException("Weight should be between 40 and 150");
        }
        if (fatPercent < 1 || fatPercent > 50) {
            throw new IllegalArgumentException("Fat percent should be between 1 and 50");
        }
        if (existsByUserId(userId)) {
            throw new UserConditionAlreadyExistsException("User condition with userId = " + userId + " already exists");
        }

        DailyKcals dailyKcals = dailyKcalsService.generateDailyKcals(gender, age, height, weight, intensity, goal, fatPercent);

        UserCondition userCondition = new UserCondition(userId, dailyKcals, gender, age, height, weight, intensity, goal, fatPercent);

        userConditionRepository.save(userCondition);
    }

    @Override
    public void updateCondition(long userId,
                                @NonNull Gender gender,
                                int age,
                                int height,
                                int weight,
                                @NonNull Intensity intensity,
                                @NonNull Goal goal,
                                double fatPercent) {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId cannot be <= 0");
        }
        if (age < 15 || age > 60) {
            throw new IllegalArgumentException("Age should be between 15 and 60");
        }
        if (height < 150 || height > 200) {
            throw new IllegalArgumentException("Height should be between 150 and 200");
        }
        if (weight < 40 || weight > 150) {
            throw new IllegalArgumentException("Weight should be between 40 and 150");
        }
        if (fatPercent < 1 || fatPercent > 50) {
            throw new IllegalArgumentException("Fat percent should be between 1 and 50");
        }
        if (!existsByUserId(userId)) {
            throw new UserConditionNotFoundException("User condition with userId = " + userId + " not found");
        }

        UserCondition userCondition = getByUserId(userId);

        DailyKcals dailyKcals = userCondition.getDailyKcals();
        DailyKcals updatedDailyKcals = dailyKcalsService.generateDailyKcals(gender, age, height, weight, intensity, goal, fatPercent);
        dailyKcals.setProtein(updatedDailyKcals.getProtein());
        dailyKcals.setFat(updatedDailyKcals.getFat());
        dailyKcals.setCarb(updatedDailyKcals.getCarb());

        userCondition.setGender(gender);
        userCondition.setAge(age);
        userCondition.setHeight(height);
        userCondition.setWeight(weight);
        userCondition.setIntensity(intensity);
        userCondition.setGoal(goal);
        userCondition.setFatPercent(fatPercent);

        userConditionRepository.save(userCondition);
    }
}
