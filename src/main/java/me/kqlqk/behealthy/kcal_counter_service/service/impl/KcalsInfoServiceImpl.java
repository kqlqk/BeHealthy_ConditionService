package me.kqlqk.behealthy.kcal_counter_service.service.impl;

import me.kqlqk.behealthy.kcal_counter_service.model.KcalsInfo;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Gender;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Goal;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Intensity;
import me.kqlqk.behealthy.kcal_counter_service.repository.KcalsInfoRepository;
import me.kqlqk.behealthy.kcal_counter_service.service.KcalsInfoService;
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
    public KcalsInfo generateDailyKcals(Gender gender, byte age, short height, short weight, Intensity intensity, Goal goal) {
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

        double proteinsPerKgWeight = 1.7;
        double fatsPerKgWeight = 1.2;
        switch (goal) {
            case LOSE:
                proteinsPerKgWeight = 2.2;
                fatsPerKgWeight = 1.5;
                fullMetabolism = (short) (fullMetabolism - 400);
                break;
            case GAIN:
                proteinsPerKgWeight = 1.9;
                fullMetabolism = (short) (fullMetabolism + 400);
                break;
        }

        KcalsInfo kcalsInfo = new KcalsInfo();
        kcalsInfo.setProtein((short) Math.round(weight * proteinsPerKgWeight));
        kcalsInfo.setFat((short) Math.round(weight * fatsPerKgWeight));
        kcalsInfo.setCarb((short) Math.round((fullMetabolism - (weight * proteinsPerKgWeight * 4) - (weight * fatsPerKgWeight * 9)) / 4));

        return kcalsInfo;
    }
}
