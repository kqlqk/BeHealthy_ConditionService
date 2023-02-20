package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.condition_service.exception.exceptions.DailyAteFoodNotFoundException;
import me.kqlqk.behealthy.condition_service.model.DailyAteFood;
import me.kqlqk.behealthy.condition_service.repository.DailyAteFoodRepository;
import me.kqlqk.behealthy.condition_service.service.DailyAteFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyAteFoodServiceImpl implements DailyAteFoodService {
    private final DailyAteFoodRepository dailyAteFoodRepository;

    @Autowired
    public DailyAteFoodServiceImpl(DailyAteFoodRepository dailyAteFoodRepository) {
        this.dailyAteFoodRepository = dailyAteFoodRepository;
    }

    @Override
    public List<DailyAteFood> getByUserId(long userId) {
        if (!dailyAteFoodRepository.existsByUserId(userId)) {
            throw new DailyAteFoodNotFoundException("DailyAteFood with userId = " + userId + " not found");
        }

        return dailyAteFoodRepository.findByUserIdOrderByIdAsc(userId);
    }

    @Override
    public void save(@NonNull DailyAteFood dailyAteFood) {
        dailyAteFood.setKcal(getKcals(dailyAteFood.getWeight(), dailyAteFood.getProtein(), dailyAteFood.getFat(), dailyAteFood.getCarb()));

        dailyAteFoodRepository.save(dailyAteFood);
    }

    @Override
    public int getKcals(double weight, int protein, int fat, int carb) {
        double kcals;

        if (protein == 0 && fat == 0) {
            kcals = weight / 100 * (carb * 4);
        }
        else if (fat == 0 && carb == 0) {
            kcals = weight / 100 * (protein * 4);
        }
        else if (protein == 0 && carb == 0) {
            kcals = weight / 100 * (fat * 9);
        }
        else if (protein == 0) {
            kcals = weight / 100 * (fat * 9 + carb * 4);
        }
        else if (fat == 0) {
            kcals = weight / 100 * (protein * 4 + carb * 4);
        }
        else if (carb == 0) {
            kcals = weight / 100 * (protein * 4 + fat * 9);
        }
        else {
            kcals = weight / 100 * (protein * 4 + fat * 9 + carb * 4);
        }

        return (int) kcals;
    }

    @Override
    public void delete(long id, long userId) {
        List<DailyAteFood> dailyAteFoodsForUser = getByUserId(userId);

        DailyAteFood dailyAteFood = dailyAteFoodsForUser
                .stream()
                .filter(product -> product.getId() == id)
                .findAny()
                .orElseThrow(() -> new DailyAteFoodNotFoundException("DailyAteFood with id = " + id + " not found for user with userId = " + userId));

        dailyAteFoodRepository.delete(dailyAteFood);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void autoDeletingEveryMidnight() {
        dailyAteFoodRepository.deleteAll();
    }
}
