package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.condition_service.model.KcalsInfo;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import me.kqlqk.behealthy.condition_service.repository.KcalsInfoRepository;
import me.kqlqk.behealthy.condition_service.service.KcalsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KcalsInfoServiceImpl implements KcalsInfoService {
    private static final int COEFFICIENT_KCALS_FOR_LOSE = 400;
    private static final int COEFFICIENT_KCALS_FOR_GAIN = 500;

    private final KcalsInfoRepository kcalsInfoRepository;

    @Autowired
    public KcalsInfoServiceImpl(KcalsInfoRepository kcalsInfoRepository) {
        this.kcalsInfoRepository = kcalsInfoRepository;
    }

    @Override
    public KcalsInfo getById(long id) {
        return kcalsInfoRepository.findById(id);
    }

    @Override
    public KcalsInfo generateDailyKcals(@NonNull Gender gender,
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

        KcalsInfo kcalsInfo = new KcalsInfo();
        int proteins = 0;
        int fats = 0;
        int carbs = 0;
        switch (goal) {
            case LOSE:
                double onLoseKCals = dailyKcals - COEFFICIENT_KCALS_FOR_LOSE;
                proteins = (int) (2.2 * leanBodyMass(weight, fatPercent));
                double onLoseKCalsWithoutProtein = onLoseKCals - proteins * 4;
                fats = (int) (onLoseKCalsWithoutProtein * 60 / 100) / 9;
                carbs = (int) (onLoseKCalsWithoutProtein * 40 / 100) / 4;

                break;

            case MAINTAIN:
                if (intensity.getCoefficient() > 1.55) {
                    proteins = (int) (1.9 * leanBodyMass(weight, fatPercent));
                    double onMaintainKCalsWithoutProtein = dailyKcals - proteins * 4;
                    fats = (int) (onMaintainKCalsWithoutProtein * 40 / 100) / 9;
                    carbs = (int) (onMaintainKCalsWithoutProtein * 60 / 100) / 4;
                } else {
                    proteins = (dailyKcals * 25 / 100) / 4;
                    fats = (dailyKcals * 35 / 100) / 9;
                    carbs = (dailyKcals * 40 / 100) / 4;
                }

                break;

            case GAIN:
                double onGainKCals = dailyKcals + COEFFICIENT_KCALS_FOR_GAIN;
                proteins = (int) (2 * leanBodyMass(weight, fatPercent));
                double onGainKCalsWithoutProtein = onGainKCals - proteins * 4;
                fats = (int) (onGainKCalsWithoutProtein * 40 / 100) / 9;
                carbs = (int) (onGainKCalsWithoutProtein * 60 / 100) / 4;

                break;
        }

        kcalsInfo.setProtein(proteins);
        kcalsInfo.setFat(fats);
        kcalsInfo.setCarb(carbs);

        return kcalsInfo;
    }

    private double formulaKatchMcArdle(int weight, double fatPercent) {
        return (370 + (21.6 * ((weight) * (100 - fatPercent) / 100)));
    }

    private double leanBodyMass(int weight, double fatPercent) {
        return (weight - (weight * fatPercent / 100));
    }
}
