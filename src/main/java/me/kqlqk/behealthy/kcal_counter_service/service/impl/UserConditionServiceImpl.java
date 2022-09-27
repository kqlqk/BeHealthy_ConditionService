package me.kqlqk.behealthy.kcal_counter_service.service.impl;

import me.kqlqk.behealthy.kcal_counter_service.exception.exceptions.UserConditionAlreadyExistsException;
import me.kqlqk.behealthy.kcal_counter_service.model.Intensity;
import me.kqlqk.behealthy.kcal_counter_service.model.KcalsInfo;
import me.kqlqk.behealthy.kcal_counter_service.model.UserCondition;
import me.kqlqk.behealthy.kcal_counter_service.repository.UserConditionRepository;
import me.kqlqk.behealthy.kcal_counter_service.service.UserConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConditionServiceImpl implements UserConditionService {
    private final UserConditionRepository userConditionRepository;

    @Autowired
    public UserConditionServiceImpl(UserConditionRepository userConditionRepository) {
        this.userConditionRepository = userConditionRepository;
    }

    @Override
    public UserCondition findById(long id) {
        return userConditionRepository.findById(id);
    }

    @Override
    public UserCondition findByUserId(long id) {
        return userConditionRepository.findByUserId(id);
    }

    @Override
    public boolean existsByUserId(long id) {
        return userConditionRepository.existsByUserId(id);
    }

    @Override
    public void createCondition(KcalsInfo kcalsInfo, short weight, Intensity intensity, long userId) {
        if(kcalsInfo == null) {
            throw new IllegalArgumentException("KcalsInfo cannot be null");
        }
        if(weight <= 30) {
            throw new IllegalArgumentException("Weight cannot be <= 30");
        }
        if(intensity == null) {
            throw new IllegalArgumentException("Intensity cannot be null");
        }
        if(userId <= 0) {
            throw new IllegalArgumentException("userId cannot be <= 0");
        }
        if(existsByUserId(userId)) {
            throw new UserConditionAlreadyExistsException("User condition with userId = " + userId + " already exists");
        }

        UserCondition userCondition = new UserCondition(kcalsInfo, weight, intensity, userId);
        userConditionRepository.save(userCondition);
    }
}
