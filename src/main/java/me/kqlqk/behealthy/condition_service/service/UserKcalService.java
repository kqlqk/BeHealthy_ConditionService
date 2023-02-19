package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.model.UserKcal;
import org.springframework.stereotype.Service;

@Service
public interface UserKcalService {
    UserKcal getByUserId(long userId);

    void save(UserKcal userKcal);

    void update(UserKcal userKcal);
}
