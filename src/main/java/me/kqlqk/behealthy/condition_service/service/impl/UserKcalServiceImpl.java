package me.kqlqk.behealthy.condition_service.service.impl;

import me.kqlqk.behealthy.condition_service.exception.exceptions.UserKcalAlreadyExistsException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserKcalNotFoundException;
import me.kqlqk.behealthy.condition_service.model.UserKcal;
import me.kqlqk.behealthy.condition_service.repository.UserKcalRepository;
import me.kqlqk.behealthy.condition_service.service.UserKcalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserKcalServiceImpl implements UserKcalService {
    private final UserKcalRepository userKcalRepository;

    @Autowired
    public UserKcalServiceImpl(UserKcalRepository userKcalRepository) {
        this.userKcalRepository = userKcalRepository;
    }

    @Override
    public UserKcal getByUserId(long userId) {
        if (!userKcalRepository.existsByUserId(userId)) {
            throw new UserKcalNotFoundException("UserKcal with userId = " + userId + " not found");
        }

        return userKcalRepository.findByUserId(userId);
    }

    @Override
    public void save(UserKcal userKcal) {
        if (userKcalRepository.existsByUserId(userKcal.getUserId())) {
            throw new UserKcalAlreadyExistsException("UserKcal with userId = " + userKcal.getUserId() + " already exists");
        }

        userKcalRepository.save(userKcal);
    }

    @Override
    public void update(UserKcal userKcal) {
        if (!userKcalRepository.existsByUserId(userKcal.getUserId())) {
            throw new UserKcalNotFoundException("UserKcal with userId = " + userKcal.getUserId() + " not found");
        }

        userKcalRepository.save(userKcal);
    }
}
