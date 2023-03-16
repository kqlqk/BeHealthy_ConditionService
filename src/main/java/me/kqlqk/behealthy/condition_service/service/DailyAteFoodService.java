package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.model.DailyAteFood;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DailyAteFoodService {
    List<DailyAteFood> getByUserId(long userId);

    void save(DailyAteFood dailyAteFood);

    void update(DailyAteFood dailyAteFood);

    int getKcals(double weight, int protein, int fat, int carb);

    void delete(long id, long userId);

    void autoChangeTodayEveryMidnight();
}
