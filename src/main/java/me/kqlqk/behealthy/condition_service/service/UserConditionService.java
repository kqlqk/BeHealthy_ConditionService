package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.model.UserCondition;
import org.springframework.stereotype.Service;

@Service
public interface UserConditionService {
    UserCondition getByUserId(long userId);

    boolean existsByUserId(long userIdl);

    double generateFatPercentForMale(int age,
                                     int fatFoldBetweenChestAndIlium,
                                     int fatFoldBetweenNavelAndLowerBelly,
                                     int fatFoldBetweenNippleAndArmpit,
                                     int fatFoldBetweenNippleAndUpperChest);

    double generateFatPercentForFemale(int age,
                                       int fatFoldBetweenShoulderAndElbow,
                                       int fatFoldBetweenChestAndIlium,
                                       int fatFoldBetweenNavelAndLowerBelly);

    void save(UserCondition userCondition);

    void update(UserCondition userCondition);
}
