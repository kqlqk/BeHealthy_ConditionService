package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.model.DailyFood;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DailyFoodService {
    DailyFood getById(long id);

    List<DailyFood> getByUserId(long userId);

    void add(long userId, String name, double weight, double kcals, double proteins, double fats, double carbs);

    void delete(long id);

    void deleteFoodEveryMidnight();
}