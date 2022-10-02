package me.kqlqk.behealthy.kcal_counter_service.service.impl;

import me.kqlqk.behealthy.kcal_counter_service.model.DailyFood;
import me.kqlqk.behealthy.kcal_counter_service.repository.DailyFoodRepository;
import me.kqlqk.behealthy.kcal_counter_service.service.DailyFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyFoodServiceImpl implements DailyFoodService {
    private final DailyFoodRepository dailyFoodRepository;

    @Autowired
    public DailyFoodServiceImpl(DailyFoodRepository dailyFoodRepository) {
        this.dailyFoodRepository = dailyFoodRepository;
    }

    @Override
    public DailyFood getById(long id) {
        return dailyFoodRepository.findById(id);
    }

    @Override
    public List<DailyFood> getByUserId(long userId) {
        return dailyFoodRepository.findByUserId(userId);
    }

    @Override
    public void add(long userId, String name, double weight, double kcals, double proteins, double fats, double carbs) {
        if (userId <= 0) {
            throw new IllegalArgumentException("UserId cannot be <= 0");
        }
        if(name == null || name.equals("")) {
            throw new IllegalArgumentException("Name cannot be null");
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

        DailyFood dailyFood = new DailyFood();
        dailyFood.setUserId(userId);
        dailyFood.setName(name);
        dailyFood.setWeight(weight);
        dailyFood.setKcals(kcals);
        dailyFood.setProteins(proteins);
        dailyFood.setFats(fats);
        dailyFood.setCarbs(carbs);

        dailyFoodRepository.save(dailyFood);
    }

    @Override
    public void delete(long id) {
        DailyFood dailyFood = getById(id);

        if (dailyFood == null) {
            throw new IllegalArgumentException("DailyFood with id = " + id + " not found");
        }

        dailyFoodRepository.delete(dailyFood);
    }
}
