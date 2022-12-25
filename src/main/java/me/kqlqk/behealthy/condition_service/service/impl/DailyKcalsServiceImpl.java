package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionNotFoundException;
import me.kqlqk.behealthy.condition_service.model.DailyKcals;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import me.kqlqk.behealthy.condition_service.repository.DailyKcalsRepository;
import me.kqlqk.behealthy.condition_service.service.DailyKcalsService;
import me.kqlqk.behealthy.condition_service.service.UserConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DailyKcalsServiceImpl implements DailyKcalsService {
    private static final int COEFFICIENT_KCALS_FOR_LOSE = 400;
    private static final int COEFFICIENT_KCALS_FOR_GAIN = 500;

    private final DailyKcalsRepository dailyKcalsRepository;
    private UserConditionService userConditionService;

    @Autowired
    public DailyKcalsServiceImpl(DailyKcalsRepository dailyKcalsRepository) {
        this.dailyKcalsRepository = dailyKcalsRepository;
    }

    @Autowired
    @Lazy
    public void setUserConditionService(UserConditionService userConditionService) {
        this.userConditionService = userConditionService;
    }

    @Override
    public DailyKcals getById(long id) {
        return dailyKcalsRepository.findById(id);
    }

    @Override
    public DailyKcals getByUserId(long id) {
        if (!userConditionService.existsByUserId(id)) {
            throw new UserConditionNotFoundException("User condition with userId = " + id + " not found");
        }

        UserCondition userCondition = userConditionService.getByUserId(id);

        return userCondition.getDailyKcals();
    }


    @Override
    public DailyKcals generateDailyKcals(@NonNull Gender gender,
                                         int age,
                                         int height,
                                         int weight,
                                         @NonNull Intensity intensity,
                                         @NonNull Goal goal,
                                         double fatPercent) {
        if (age <= 10) {
            throw new IllegalArgumentException("Age cannot be <= 10");
        }
        if (height <= 130) {
            throw new IllegalArgumentException("Height cannot be <= 130");
        }
        if (weight <= 30) {
            throw new IllegalArgumentException("Weight cannot be <= 30");
        }
        if (fatPercent < 1 || fatPercent > 50) {
            throw new IllegalArgumentException("Fat percent cannot be < 1 or > 50");
        }

        int dailyKcals = (int) (formulaKatchMcArdle(weight, fatPercent) * intensity.getCoefficient());

        DailyKcals dailyKcalsModel = new DailyKcals();
        int proteins = 0;
        int fats = 0;
        int carbs = 0;

        switch (goal) {
            case LOSE:
                double onLoseKcals = dailyKcals - COEFFICIENT_KCALS_FOR_LOSE;
                proteins = (int) (2.2 * leanBodyMass(weight, fatPercent));
                double onLoseKcalsWithoutProtein = onLoseKcals - proteins * 4;
                fats = (int) (onLoseKcalsWithoutProtein * 60 / 100) / 9;
                carbs = (int) (onLoseKcalsWithoutProtein * 40 / 100) / 4;

                break;

            case MAINTAIN:
                if (intensity.getCoefficient() > 1.55) {
                    proteins = (int) (1.9 * leanBodyMass(weight, fatPercent));
                    double onMaintainKcalsWithoutProtein = dailyKcals - proteins * 4;
                    fats = (int) (onMaintainKcalsWithoutProtein * 40 / 100) / 9;
                    carbs = (int) (onMaintainKcalsWithoutProtein * 60 / 100) / 4;
                } else {
                    proteins = (dailyKcals * 25 / 100) / 4;
                    fats = (dailyKcals * 35 / 100) / 9;
                    carbs = (dailyKcals * 40 / 100) / 4;
                }

                break;

            case GAIN:
                double onGainKcals = dailyKcals + COEFFICIENT_KCALS_FOR_GAIN;
                proteins = (int) (2.1 * leanBodyMass(weight, fatPercent));
                double onGainKCalsWithoutProtein = onGainKcals - proteins * 4;
                fats = (int) (onGainKCalsWithoutProtein * 40 / 100) / 9;
                carbs = (int) (onGainKCalsWithoutProtein * 60 / 100) / 4;

                break;
        }

        dailyKcalsModel.setProtein(proteins);
        dailyKcalsModel.setFat(fats);
        dailyKcalsModel.setCarb(carbs);

        return dailyKcalsModel;
    }

    private double formulaKatchMcArdle(int weight, double fatPercent) {
        return (370 + (21.6 * ((weight) * (100 - fatPercent) / 100)));
    }

    private double leanBodyMass(int weight, double fatPercent) {
        return (weight - (weight * fatPercent / 100));
    }
}
