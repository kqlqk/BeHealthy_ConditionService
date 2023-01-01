package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.condition_service.exception.exceptions.FoodNotFoundException;
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
    public DailyAteFood getById(long id) {
        return dailyAteFoodRepository.findById(id);
    }

    @Override
    public List<DailyAteFood> getByUserId(long userId) {
        return dailyAteFoodRepository.findByUserId(userId);
    }

    @Override
    public void add(long userId, @NonNull String name, double weight, double kcals, double proteins, double fats, double carbs) {
        if (userId <= 0) {
            throw new IllegalArgumentException("UserId cannot be <= 0");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight cannot be <= 0");
        }
        if (kcals < 0) {
            throw new IllegalArgumentException("Kcals cannot be < 0");
        }
        if (proteins < 0) {
            throw new IllegalArgumentException("Proteins cannot be < 0");
        }
        if (fats < 0) {
            throw new IllegalArgumentException("Fats cannot be < 0");
        }
        if (carbs < 0) {
            throw new IllegalArgumentException("Carbs cannot be < 0");
        }

        DailyAteFood dailyAteFood = new DailyAteFood();
        dailyAteFood.setUserId(userId);
        dailyAteFood.setName(name);
        dailyAteFood.setWeight(weight);
        dailyAteFood.setKcals(kcals);
        dailyAteFood.setProteins(proteins);
        dailyAteFood.setFats(fats);
        dailyAteFood.setCarbs(carbs);

        dailyAteFoodRepository.save(dailyAteFood);
    }

    @Override
    public void delete(long id, long userId) {
        List<DailyAteFood> dailyAteFoodsForUser = getByUserId(userId);

        if (dailyAteFoodsForUser.isEmpty()) {
            throw new FoodNotFoundException("User's daily food not found");
        }

        DailyAteFood dailyAteFood = dailyAteFoodsForUser
                .stream()
                .filter(product -> product.getId() == id)
                .findAny()
                .orElseThrow(() -> new FoodNotFoundException("Daily food with id = " + id + " not found for user with userId = " + id));

        dailyAteFoodRepository.delete(dailyAteFood);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void deleteFoodEveryMidnight() {
        dailyAteFoodRepository.deleteAll();
    }
}
