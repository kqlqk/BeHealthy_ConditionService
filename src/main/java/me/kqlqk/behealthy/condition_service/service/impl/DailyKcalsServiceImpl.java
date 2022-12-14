package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.condition_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.condition_service.exception.exceptions.KcalsException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionNotFoundException;
import me.kqlqk.behealthy.condition_service.model.DailyKcals;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import me.kqlqk.behealthy.condition_service.repository.DailyKcalsRepository;
import me.kqlqk.behealthy.condition_service.service.DailyKcalsService;
import me.kqlqk.behealthy.condition_service.service.UserConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class DailyKcalsServiceImpl implements DailyKcalsService {
    private static final int COEFFICIENT_KCALS_FOR_LOSE = 400;
    private static final int COEFFICIENT_KCALS_FOR_GAIN = 500;

    private final DailyKcalsRepository dailyKcalsRepository;
    private final Validator validator;
    private UserConditionService userConditionService;

    @Autowired
    public DailyKcalsServiceImpl(DailyKcalsRepository dailyKcalsRepository, Validator validator) {
        this.dailyKcalsRepository = dailyKcalsRepository;
        this.validator = validator;
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
    public DailyKcals generateDailyKcals(@NonNull UserConditionDTO userConditionDTO) {
        Set<ConstraintViolation<UserConditionDTO>> constraintViolations = validator.validate(userConditionDTO);

        if (!constraintViolations.isEmpty()) {
            throw new KcalsException(constraintViolations.iterator().next().getMessage());
        }

        int dailyKcals = (int) (formulaKatchMcArdle(userConditionDTO.getWeight(), userConditionDTO.getFatPercent()) * userConditionDTO.getIntensity().getCoefficient());

        DailyKcals dailyKcalsModel = new DailyKcals();
        int proteins = 0;
        int fats = 0;
        int carbs = 0;

        switch (userConditionDTO.getGoal()) {
            case LOSE:
                double onLoseKcals = dailyKcals - COEFFICIENT_KCALS_FOR_LOSE;
                proteins = (int) (2.2 * leanBodyMass(userConditionDTO.getWeight(), userConditionDTO.getFatPercent()));
                double onLoseKcalsWithoutProtein = onLoseKcals - proteins * 4;
                fats = (int) (onLoseKcalsWithoutProtein * 60 / 100) / 9;
                carbs = (int) (onLoseKcalsWithoutProtein * 40 / 100) / 4;

                break;

            case MAINTAIN:
                if (userConditionDTO.getIntensity().getCoefficient() > 1.55) {
                    proteins = (int) (1.9 * leanBodyMass(userConditionDTO.getWeight(), userConditionDTO.getFatPercent()));
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
                proteins = (int) (2.1 * leanBodyMass(userConditionDTO.getWeight(), userConditionDTO.getFatPercent()));
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
