package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.condition_service.dto.DailyAteFoodDTO;
import me.kqlqk.behealthy.condition_service.exception.exceptions.FoodException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.FoodNotFoundException;
import me.kqlqk.behealthy.condition_service.model.DailyAteFood;
import me.kqlqk.behealthy.condition_service.repository.DailyAteFoodRepository;
import me.kqlqk.behealthy.condition_service.service.DailyAteFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class DailyAteFoodServiceImpl implements DailyAteFoodService {
    private final DailyAteFoodRepository dailyAteFoodRepository;
    private final Validator validator;

    @Autowired
    public DailyAteFoodServiceImpl(DailyAteFoodRepository dailyAteFoodRepository, Validator validator) {
        this.dailyAteFoodRepository = dailyAteFoodRepository;
        this.validator = validator;
    }

    @Override
    public List<DailyAteFood> getByUserId(long userId) {
        return dailyAteFoodRepository.findByUserId(userId);
    }

    @Override
    public void add(@NonNull DailyAteFoodDTO dailyAteFoodDTO) {
        Set<ConstraintViolation<DailyAteFoodDTO>> constraintViolations = validator.validate(dailyAteFoodDTO);

        if (!constraintViolations.isEmpty()) {
            throw new FoodException(constraintViolations.iterator().next().getMessage());
        }

        DailyAteFood dailyAteFood = new DailyAteFood();
        dailyAteFood.setUserId(dailyAteFoodDTO.getUserId());
        dailyAteFood.setName(dailyAteFoodDTO.getName());
        dailyAteFood.setWeight(dailyAteFoodDTO.getWeight());
        dailyAteFood.setKcals(dailyAteFoodDTO.getKcals());
        dailyAteFood.setProteins(dailyAteFoodDTO.getProteins());
        dailyAteFood.setFats(dailyAteFoodDTO.getFats());
        dailyAteFood.setCarbs(dailyAteFoodDTO.getCarbs());

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
