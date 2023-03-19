package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.condition_service.exception.exceptions.DailyAteFoodAlreadyExistsException;
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
        List<DailyAteFood> res = dailyAteFoodRepository.findByUserIdOrderByIdAsc(userId)
                .orElseThrow(() -> new DailyAteFoodNotFoundException("DailyAteFood with userId = " + userId + " not found"));

        if (res.isEmpty()) {
            throw new DailyAteFoodNotFoundException("DailyAteFood with userId = " + userId + " not found");
        }

        return res;
    }

    @Override
    public DailyAteFood getByNameAndUserId(String name, long userId) {
        return dailyAteFoodRepository.findByNameAndUserId(name, userId)
                .orElseThrow(() -> new DailyAteFoodNotFoundException("DailyAteFood with name = " + name + " and userId = " + userId + " not found"));
    }

    @Override
    public void save(@NonNull DailyAteFood dailyAteFood) {
        if (dailyAteFoodRepository.existsByNameAndUserId(dailyAteFood.getName(), dailyAteFood.getUserId())) {
            throw new DailyAteFoodAlreadyExistsException("DailyAteFood with name = " + dailyAteFood.getName() +
                                                                 " and userId = " + dailyAteFood.getUserId() + " already exists");
        }

        dailyAteFood.setId(0);
        dailyAteFood.setKcal(getKcals(dailyAteFood.getWeight(), dailyAteFood.getProtein(), dailyAteFood.getFat(), dailyAteFood.getCarb()));

        dailyAteFoodRepository.save(dailyAteFood);
    }

    @Override
    public void update(@NonNull DailyAteFood dailyAteFood) {
        dailyAteFood.setId(getByNameAndUserId(dailyAteFood.getName(), dailyAteFood.getUserId()).getId());
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
    public void delete(String name, long userId) {
        dailyAteFoodRepository.delete(getByNameAndUserId(name, userId));
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void autoChangeTodayEveryMidnight() {
        dailyAteFoodRepository.findAll().forEach(e -> {
            e.setToday(false);
            update(e);
        });
    }
}
