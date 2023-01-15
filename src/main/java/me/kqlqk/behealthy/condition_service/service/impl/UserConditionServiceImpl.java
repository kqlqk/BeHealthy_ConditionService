package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.condition_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionAlreadyExistsException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionNotFoundException;
import me.kqlqk.behealthy.condition_service.model.DailyKcals;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.repository.UserConditionRepository;
import me.kqlqk.behealthy.condition_service.service.DailyKcalsService;
import me.kqlqk.behealthy.condition_service.service.UserConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class UserConditionServiceImpl implements UserConditionService {
    private final UserConditionRepository userConditionRepository;
    private final Validator validator;
    private DailyKcalsService dailyKcalsService;

    @Autowired
    public UserConditionServiceImpl(UserConditionRepository userConditionRepository, Validator validator) {
        this.userConditionRepository = userConditionRepository;
        this.validator = validator;
    }

    @Autowired
    @Lazy
    public void setDailyKcalsService(DailyKcalsService dailyKcalsService) {
        this.dailyKcalsService = dailyKcalsService;
    }


    @Override
    public UserCondition getByUserId(long id) {
        return userConditionRepository.findByUserId(id);
    }

    @Override
    public boolean existsByUserId(long id) {
        return userConditionRepository.existsByUserId(id);
    }

    @Override
    public void generateAndSaveConditionWithoutFatPercentForMale(@NonNull UserConditionDTO userConditionDTO,
                                                                 int fatFoldBetweenChestAndIlium,
                                                                 int fatFoldBetweenNavelAndLowerBelly,
                                                                 int fatFoldBetweenNippleAndArmpit,
                                                                 int fatFoldBetweenNippleAndUpperChest) {
        Set<ConstraintViolation<UserConditionDTO>> constraintViolationsAge = validator.validateProperty(userConditionDTO, "age");
        if (!constraintViolationsAge.isEmpty()) {
            throw new UserConditionException(constraintViolationsAge.iterator().next().getMessage());
        }

        Set<ConstraintViolation<UserConditionDTO>> constraintViolationsHeight = validator.validateProperty(userConditionDTO, "height");
        if (!constraintViolationsHeight.isEmpty()) {
            throw new UserConditionException(constraintViolationsHeight.iterator().next().getMessage());
        }

        Set<ConstraintViolation<UserConditionDTO>> constraintViolationsWeight = validator.validateProperty(userConditionDTO, "weight");
        if (!constraintViolationsWeight.isEmpty()) {
            throw new UserConditionException(constraintViolationsWeight.iterator().next().getMessage());
        }

        Set<ConstraintViolation<UserConditionDTO>> constraintViolationsIntensity = validator.validateProperty(userConditionDTO, "intensity");
        if (!constraintViolationsIntensity.isEmpty()) {
            throw new UserConditionException(constraintViolationsIntensity.iterator().next().getMessage());
        }

        Set<ConstraintViolation<UserConditionDTO>> constraintViolationsGoal = validator.validateProperty(userConditionDTO, "goal");
        if (!constraintViolationsGoal.isEmpty()) {
            throw new UserConditionException(constraintViolationsGoal.iterator().next().getMessage());
        }


        if (fatFoldBetweenChestAndIlium < 2 || fatFoldBetweenChestAndIlium > 50) {
            throw new IllegalArgumentException("Fat fold between chest and ilium should be between 2 and 50");
        }
        if (fatFoldBetweenNavelAndLowerBelly < 5 || fatFoldBetweenNavelAndLowerBelly > 70) {
            throw new IllegalArgumentException("Fat fold between navel and lower belly should be between 5 and 70");
        }
        if (fatFoldBetweenNippleAndArmpit < 2 || fatFoldBetweenNippleAndArmpit > 50) {
            throw new IllegalArgumentException("Fat fold between nipple and armpit should be between 2 and 50");
        }
        if (fatFoldBetweenNippleAndUpperChest < 2 || fatFoldBetweenNippleAndUpperChest > 50) {
            throw new IllegalArgumentException("Fat fold between nipple and upper chest should be between 2 and 50");
        }
        if (existsByUserId(userConditionDTO.getUserId())) {
            throw new UserConditionAlreadyExistsException("User condition with userId = " + userConditionDTO.getUserId() + " already exists");
        }

        userConditionDTO.setFatPercent(getFatPercent(
                Gender.MALE,
                fatFoldBetweenChestAndIlium,
                fatFoldBetweenNavelAndLowerBelly,
                fatFoldBetweenNippleAndArmpit,
                fatFoldBetweenNippleAndUpperChest,
                0,
                userConditionDTO.getAge()));

        generateAndSaveCondition(userConditionDTO);
    }

    @Override
    public void generateAndSaveConditionWithoutFatPercentForFemale(@NonNull UserConditionDTO userConditionDTO,
                                                                   int fatFoldBetweenShoulderAndElbow,
                                                                   int fatFoldBetweenChestAndIlium,
                                                                   int fatFoldBetweenNavelAndLowerBelly) {
        Set<ConstraintViolation<UserConditionDTO>> constraintViolationsAge = validator.validateProperty(userConditionDTO, "age");
        if (!constraintViolationsAge.isEmpty()) {
            throw new UserConditionException(constraintViolationsAge.iterator().next().getMessage());
        }

        Set<ConstraintViolation<UserConditionDTO>> constraintViolationsHeight = validator.validateProperty(userConditionDTO, "height");
        if (!constraintViolationsHeight.isEmpty()) {
            throw new UserConditionException(constraintViolationsHeight.iterator().next().getMessage());
        }

        Set<ConstraintViolation<UserConditionDTO>> constraintViolationsWeight = validator.validateProperty(userConditionDTO, "weight");
        if (!constraintViolationsWeight.isEmpty()) {
            throw new UserConditionException(constraintViolationsWeight.iterator().next().getMessage());
        }

        Set<ConstraintViolation<UserConditionDTO>> constraintViolationsIntensity = validator.validateProperty(userConditionDTO, "intensity");
        if (!constraintViolationsIntensity.isEmpty()) {
            throw new UserConditionException(constraintViolationsIntensity.iterator().next().getMessage());
        }

        Set<ConstraintViolation<UserConditionDTO>> constraintViolationsGoal = validator.validateProperty(userConditionDTO, "goal");
        if (!constraintViolationsGoal.isEmpty()) {
            throw new UserConditionException(constraintViolationsGoal.iterator().next().getMessage());
        }


        if (fatFoldBetweenShoulderAndElbow < 2 || fatFoldBetweenShoulderAndElbow > 50) {
            throw new IllegalArgumentException("Fat fold between shoulder and elbow should be between 5 and 50");
        }
        if (fatFoldBetweenChestAndIlium < 2 || fatFoldBetweenChestAndIlium > 50) {
            throw new IllegalArgumentException("Fat fold between chest and ilium should be between 5 and 50");
        }
        if (fatFoldBetweenNavelAndLowerBelly < 5 || fatFoldBetweenNavelAndLowerBelly > 70) {
            throw new IllegalArgumentException("Fat fold between navel and lower belly should be between 5 and 70");
        }
        if (existsByUserId(userConditionDTO.getUserId())) {
            throw new UserConditionAlreadyExistsException("User condition with userId = " + userConditionDTO.getUserId() + " already exists");
        }

        userConditionDTO.setFatPercent(getFatPercent(
                Gender.FEMALE,
                fatFoldBetweenChestAndIlium,
                fatFoldBetweenNavelAndLowerBelly,
                0,
                0,
                fatFoldBetweenShoulderAndElbow,
                userConditionDTO.getAge()));

        generateAndSaveCondition(userConditionDTO);
    }

    protected double getFatPercent(Gender gender,
                                   int fatFoldBetweenChestAndIlium,
                                   int fatFoldBetweenNavelAndLowerBelly,
                                   int fatFoldBetweenNippleAndArmpit,
                                   int fatFoldBetweenNippleAndUpperChest,
                                   int fatFoldBetweenShoulderAndElbow,
                                   int age) {
        switch (gender) {
            case MALE:
                return (
                        (0.27784 * (fatFoldBetweenChestAndIlium + fatFoldBetweenNavelAndLowerBelly + fatFoldBetweenNippleAndArmpit + fatFoldBetweenNippleAndUpperChest)) -
                                (0.00053 * (Math.pow(fatFoldBetweenChestAndIlium, 2) + Math.pow(fatFoldBetweenNavelAndLowerBelly, 2) + Math.pow(fatFoldBetweenNippleAndArmpit, 2) + Math.pow(fatFoldBetweenNippleAndUpperChest, 2))) +
                                (0.12437 * age)) - 3.28791;

            case FEMALE:
                return (
                        (0.41563 * (fatFoldBetweenShoulderAndElbow + fatFoldBetweenChestAndIlium + fatFoldBetweenNavelAndLowerBelly)) -
                                (0.00112 * (Math.pow(fatFoldBetweenShoulderAndElbow, 2) + Math.pow(fatFoldBetweenChestAndIlium, 2) + Math.pow(fatFoldBetweenNavelAndLowerBelly, 2))) +
                                (0.03661 * age)) - 4.03653;
        }

        return 0;
    }

    @Override
    public void generateAndSaveCondition(@NonNull UserConditionDTO userConditionDTO) {
        Set<ConstraintViolation<UserConditionDTO>> constraintViolations = validator.validate(userConditionDTO);

        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        if (existsByUserId(userConditionDTO.getUserId())) {
            throw new UserConditionAlreadyExistsException("User condition with userId = " + userConditionDTO.getUserId() + " already exists");
        }

        DailyKcals dailyKcals = dailyKcalsService.generateDailyKcals(userConditionDTO);

        UserCondition userCondition = new UserCondition(
                userConditionDTO.getUserId(),
                dailyKcals,
                userConditionDTO.getGender(),
                userConditionDTO.getAge(),
                userConditionDTO.getHeight(),
                userConditionDTO.getWeight(),
                userConditionDTO.getIntensity(),
                userConditionDTO.getGoal(),
                userConditionDTO.getFatPercent());

        userConditionRepository.save(userCondition);
    }

    @Override
    public void updateCondition(@NonNull UserConditionDTO userConditionDTO) {
        Set<ConstraintViolation<UserConditionDTO>> constraintViolationsAge = validator.validate(userConditionDTO);
        if (!constraintViolationsAge.isEmpty()) {
            throw new UserConditionException(constraintViolationsAge.iterator().next().getMessage());
        }

        if (!existsByUserId(userConditionDTO.getUserId())) {
            throw new UserConditionNotFoundException("User condition with userId = " + userConditionDTO.getUserId() + " not found");
        }

        UserCondition userCondition = getByUserId(userConditionDTO.getUserId());

        DailyKcals dailyKcals = userCondition.getDailyKcals();
        DailyKcals updatedDailyKcals = dailyKcalsService.generateDailyKcals(userConditionDTO);
        dailyKcals.setProtein(updatedDailyKcals.getProtein());
        dailyKcals.setFat(updatedDailyKcals.getFat());
        dailyKcals.setCarb(updatedDailyKcals.getCarb());

        userCondition.setGender(userConditionDTO.getGender());
        userCondition.setAge(userConditionDTO.getAge());
        userCondition.setHeight(userConditionDTO.getHeight());
        userCondition.setWeight(userConditionDTO.getWeight());
        userCondition.setIntensity(userConditionDTO.getIntensity());
        userCondition.setGoal(userConditionDTO.getGoal());
        userCondition.setFatPercent(userConditionDTO.getFatPercent());

        userConditionRepository.save(userCondition);
    }
}
