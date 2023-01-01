package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.model.DailyAteFood;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DailyAteFoodService {
    DailyAteFood getById(long id);

    List<DailyAteFood> getByUserId(long userId);

    void add(long userId, String name, double weight, double kcals, double proteins, double fats, double carbs);

    void delete(long id, long userId);

    void deleteFoodEveryMidnight();
}
