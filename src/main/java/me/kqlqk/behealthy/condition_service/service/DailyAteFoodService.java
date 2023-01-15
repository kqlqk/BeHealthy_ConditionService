package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.dto.DailyAteFoodDTO;
import me.kqlqk.behealthy.condition_service.model.DailyAteFood;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DailyAteFoodService {
    List<DailyAteFood> getByUserId(long userId);

    void add(DailyAteFoodDTO dailyAteFoodDTO);

    void delete(long id, long userId);

    void deleteFoodEveryMidnight();
}
