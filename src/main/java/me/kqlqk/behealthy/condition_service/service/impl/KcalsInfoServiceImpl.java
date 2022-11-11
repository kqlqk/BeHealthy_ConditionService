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
                                        byte age,
                                        short height,
                                        short weight,
                                        @NonNull Intensity intensity,
                                        @NonNull Goal goal) {
        if (age <= 10) {
            throw new IllegalArgumentException("Age cannot be <= 10");
        }
        if (height <= 130) {
            throw new IllegalArgumentException("Height cannot be <= 130");
        }
        if (weight <= 30) {
            throw new IllegalArgumentException("Weight cannot be <= 30");
        }

        double basicMetabolism = 0;
        switch (gender) {
            case MALE:
                basicMetabolism = 66.5 + (13.75 * weight) + (5.003 * height) - (6.775 * age);
                break;

            case FEMALE:
                basicMetabolism = 655.1 + (9.563 * weight) + (1.85 * height) - (4.676 * age);
                break;
        }

        short fullMetabolism = (short) Math.round(basicMetabolism * intensity.getActivity());

        double proteins = 0;
        double fats = 0;
        double carbs = 0;

        switch (goal) {
            case MAINTAIN:
                proteins = fullMetabolism * 0.3 / 4;
                fats = fullMetabolism * 0.25 / 9;
                carbs = (fullMetabolism - (proteins * 4) - (fats * 9)) / 4;

                if (carbs > 300) {
                    carbs = 300;
                    fats = (fullMetabolism - (proteins * 4) - (carbs * 4)) / 9;
                }
                break;
            case LOSE:
                fullMetabolism = (short) (fullMetabolism - 400);
                proteins = fullMetabolism * 0.35 / 4;

                fats = fullMetabolism * 0.3 / 9;

                if (fats > 180) {
                    fats = 180;
                }

                carbs = (fullMetabolism - (proteins * 4) - (fats * 9)) / 4;
                break;
            case GAIN:
                fullMetabolism = (short) (fullMetabolism + 400);
                proteins = fullMetabolism * 0.3 / 4;

                fats = fullMetabolism * 0.2 / 9;
                carbs = (fullMetabolism - (proteins * 4) - (fats * 9)) / 4;
                break;
        }

        if (proteins > 200) {
            proteins = 200;
        }

        KcalsInfo kcalsInfo = new KcalsInfo();
        kcalsInfo.setProtein((short) Math.round(proteins));
        kcalsInfo.setFat((short) Math.round(fats));
        kcalsInfo.setCarb((short) carbs);

        return kcalsInfo;
    }
}
