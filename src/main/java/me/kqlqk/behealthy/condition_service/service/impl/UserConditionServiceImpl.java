package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionAlreadyExistsException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionNotFoundException;
import me.kqlqk.behealthy.condition_service.model.DailyKcal;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import me.kqlqk.behealthy.condition_service.repository.UserConditionRepository;
import me.kqlqk.behealthy.condition_service.service.DailyKcalService;
import me.kqlqk.behealthy.condition_service.service.UserConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UserConditionServiceImpl implements UserConditionService {
    private final UserConditionRepository userConditionRepository;
    private DailyKcalService dailyKcalService;

    @Autowired
    public UserConditionServiceImpl(UserConditionRepository userConditionRepository) {
        this.userConditionRepository = userConditionRepository;
    }

    @Autowired
    @Lazy
    public void setDailyKcalsService(DailyKcalService dailyKcalService) {
        this.dailyKcalService = dailyKcalService;
    }


    @Override
    public UserCondition getByUserId(long userId) {
        if (!existsByUserId(userId)) {
            throw new UserConditionNotFoundException("User condition with userId = " + userId + " not found");
        }

        return userConditionRepository.findByUserId(userId);
    }

    @Override
    public boolean existsByUserId(long id) {
        return userConditionRepository.existsByUserId(id);
    }

    @Override
    public double generateFatPercentForMale(int age,
                                            int fatFoldBetweenChestAndIlium,
                                            int fatFoldBetweenNavelAndLowerBelly,
                                            int fatFoldBetweenNippleAndArmpit,
                                            int fatFoldBetweenNippleAndUpperChest) {
        return (
                (0.27784 * (fatFoldBetweenChestAndIlium + fatFoldBetweenNavelAndLowerBelly + fatFoldBetweenNippleAndArmpit + fatFoldBetweenNippleAndUpperChest)) -
                        (0.00053 *
                                (Math.pow(fatFoldBetweenChestAndIlium, 2) +
                                        Math.pow(fatFoldBetweenNavelAndLowerBelly, 2) +
                                        Math.pow(fatFoldBetweenNippleAndArmpit, 2) +
                                        Math.pow(fatFoldBetweenNippleAndUpperChest, 2))) +
                        (0.12437 * age)) - 3.28791;
    }

    @Override
    public double generateFatPercentForFemale(int age,
                                              int fatFoldBetweenShoulderAndElbow,
                                              int fatFoldBetweenChestAndIlium,
                                              int fatFoldBetweenNavelAndLowerBelly) {
        return (
                (0.41563 * (fatFoldBetweenShoulderAndElbow + fatFoldBetweenChestAndIlium + fatFoldBetweenNavelAndLowerBelly)) -
                        (0.00112 *
                                (Math.pow(fatFoldBetweenShoulderAndElbow, 2) +
                                        Math.pow(fatFoldBetweenChestAndIlium, 2) +
                                        Math.pow(fatFoldBetweenNavelAndLowerBelly, 2))) +
                        (0.03661 * age)) - 4.03653;
    }


    @Override
    public void save(@NonNull UserCondition userCondition) {
        if (existsByUserId(userCondition.getUserId())) {
            throw new UserConditionAlreadyExistsException("User condition with userId = " + userCondition.getUserId() + " already exists");
        }

        DailyKcal dailyKcal = dailyKcalService.generateDailyKcals(userCondition.getWeight(),
                                                                  userCondition.getFatPercent(),
                                                                  userCondition.getActivity(),
                                                                  userCondition.getGoal());
        userCondition.setDailyKcal(dailyKcal);

        userConditionRepository.save(userCondition);
    }

    @Override
    public void update(@NonNull UserCondition userCondition) {
        if (!existsByUserId(userCondition.getUserId())) {
            throw new UserConditionNotFoundException("User condition with userId = " + userCondition.getUserId() + " not found");
        }

        DailyKcal dailyKcal = userCondition.getDailyKcal();
        DailyKcal updatedDailyKcal = dailyKcalService.generateDailyKcals(userCondition.getWeight(),
                                                                         userCondition.getFatPercent(),
                                                                         userCondition.getActivity(),
                                                                         userCondition.getGoal());
        dailyKcal.setProtein(updatedDailyKcal.getProtein());
        dailyKcal.setFat(updatedDailyKcal.getFat());
        dailyKcal.setCarb(updatedDailyKcal.getCarb());

        userConditionRepository.save(userCondition);
    }
}
