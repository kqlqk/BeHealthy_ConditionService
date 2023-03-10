package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.condition_service.model.DailyKcal;
import me.kqlqk.behealthy.condition_service.model.enums.Activity;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.service.DailyKcalService;
import org.springframework.stereotype.Service;

@Service
public class DailyKcalServiceImpl implements DailyKcalService {
    private static final int COEFFICIENT_KCALS_FOR_LOSE = 400;
    private static final int COEFFICIENT_KCALS_FOR_GAIN = 500;

    @Override
    public DailyKcal generateDailyKcals(int weight, double fatPercent, @NonNull Activity activity, @NonNull Goal goal) {
        int dailyKcals = (int) (formulaKatchMcArdle(weight, fatPercent) * activity.getCoefficient());

        DailyKcal dailyKcalModel = new DailyKcal();
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
                if (activity.getCoefficient() > 1.55) {
                    proteins = (int) (1.9 * leanBodyMass(weight, fatPercent));
                    double onMaintainKcalsWithoutProtein = dailyKcals - proteins * 4;
                    fats = (int) (onMaintainKcalsWithoutProtein * 40 / 100) / 9;
                    carbs = (int) (onMaintainKcalsWithoutProtein * 60 / 100) / 4;
                }
                else {
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

        dailyKcalModel.setProtein(proteins);
        dailyKcalModel.setFat(fats);
        dailyKcalModel.setCarb(carbs);

        return dailyKcalModel;
    }

    private double formulaKatchMcArdle(int weight, double fatPercent) {
        return (370 + (21.6 * ((weight) * (100 - fatPercent) / 100)));
    }

    private double leanBodyMass(int weight, double fatPercent) {
        return (weight - (weight * fatPercent / 100));
    }
}
