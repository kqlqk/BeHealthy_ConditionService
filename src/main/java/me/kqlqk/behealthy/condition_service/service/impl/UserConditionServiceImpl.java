package me.kqlqk.behealthy.condition_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.condition_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.condition_service.dto.UserConditionWithoutFatPercentFemaleDTO;
import me.kqlqk.behealthy.condition_service.dto.UserConditionWithoutFatPercentMaleDTO;
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
    public void generateAndSaveConditionWithoutFatPercent(UserConditionWithoutFatPercentMaleDTO userConditionWithoutFatPercentMaleDTO,
                                                          UserConditionWithoutFatPercentFemaleDTO userConditionWithoutFatPercentFemaleDTO) {
        if (userConditionWithoutFatPercentMaleDTO != null) {
            generateAndSaveConditionWithoutFatPercentForMale(userConditionWithoutFatPercentMaleDTO);
        }
        else {
            generateAndSaveConditionWithoutFatPercentForFemale(userConditionWithoutFatPercentFemaleDTO);
        }
    }

    private void generateAndSaveConditionWithoutFatPercentForMale(
            @NonNull UserConditionWithoutFatPercentMaleDTO userConditionWithoutFatPercentMaleDTO) {

        Set<ConstraintViolation<UserConditionWithoutFatPercentMaleDTO>> constraintViolations =
                validator.validate(userConditionWithoutFatPercentMaleDTO);

        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }


        UserConditionDTO userConditionDTO = new UserConditionDTO();
        userConditionDTO.setUserId(userConditionWithoutFatPercentMaleDTO.getUserId());
        userConditionDTO.setGoal(userConditionWithoutFatPercentMaleDTO.getGoal());
        userConditionDTO.setGender(Gender.MALE);
        userConditionDTO.setAge(userConditionWithoutFatPercentMaleDTO.getAge());
        userConditionDTO.setHeight(userConditionWithoutFatPercentMaleDTO.getHeight());
        userConditionDTO.setWeight(userConditionWithoutFatPercentMaleDTO.getWeight());
        userConditionDTO.setIntensity(userConditionWithoutFatPercentMaleDTO.getIntensity());
        userConditionDTO.setFatPercent(getFatPercent(userConditionWithoutFatPercentMaleDTO, null));

        generateAndSaveCondition(userConditionDTO);
    }

    private void generateAndSaveConditionWithoutFatPercentForFemale(
            @NonNull UserConditionWithoutFatPercentFemaleDTO userConditionWithoutFatPercentFemaleDTO) {

        Set<ConstraintViolation<UserConditionWithoutFatPercentFemaleDTO>> constraintViolations =
                validator.validate(userConditionWithoutFatPercentFemaleDTO);

        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        UserConditionDTO userConditionDTO = new UserConditionDTO();
        userConditionDTO.setUserId(userConditionWithoutFatPercentFemaleDTO.getUserId());
        userConditionDTO.setGoal(userConditionWithoutFatPercentFemaleDTO.getGoal());
        userConditionDTO.setGender(Gender.FEMALE);
        userConditionDTO.setAge(userConditionWithoutFatPercentFemaleDTO.getAge());
        userConditionDTO.setHeight(userConditionWithoutFatPercentFemaleDTO.getHeight());
        userConditionDTO.setWeight(userConditionWithoutFatPercentFemaleDTO.getWeight());
        userConditionDTO.setIntensity(userConditionWithoutFatPercentFemaleDTO.getIntensity());
        userConditionDTO.setFatPercent(getFatPercent(null, userConditionWithoutFatPercentFemaleDTO));

        generateAndSaveCondition(userConditionDTO);
    }

    protected double getFatPercent(UserConditionWithoutFatPercentMaleDTO userConditionWithoutFatPercentMaleDTO,
                                   UserConditionWithoutFatPercentFemaleDTO userConditionWithoutFatPercentFemaleDTO) {
        if (userConditionWithoutFatPercentMaleDTO != null) {
            return (
                    (0.27784 * (userConditionWithoutFatPercentMaleDTO.getFatFoldBetweenChestAndIlium() +
                            userConditionWithoutFatPercentMaleDTO.getFatFoldBetweenNavelAndLowerBelly() +
                            userConditionWithoutFatPercentMaleDTO.getFatFoldBetweenNippleAndArmpit() +
                            userConditionWithoutFatPercentMaleDTO.getFatFoldBetweenNippleAndUpperChest())) -
                            (0.00053 * (Math.pow(userConditionWithoutFatPercentMaleDTO.getFatFoldBetweenChestAndIlium(), 2) +
                                    Math.pow(userConditionWithoutFatPercentMaleDTO.getFatFoldBetweenNavelAndLowerBelly(), 2) +
                                    Math.pow(userConditionWithoutFatPercentMaleDTO.getFatFoldBetweenNippleAndArmpit(), 2) +
                                    Math.pow(userConditionWithoutFatPercentMaleDTO.getFatFoldBetweenNippleAndUpperChest(), 2))) +
                            (0.12437 * userConditionWithoutFatPercentMaleDTO.getAge())) - 3.28791;
        }
        else {
            return (
                    (0.41563 * (userConditionWithoutFatPercentFemaleDTO.getFatFoldBetweenShoulderAndElbow() +
                            userConditionWithoutFatPercentFemaleDTO.getFatFoldBetweenChestAndIlium() +
                            userConditionWithoutFatPercentFemaleDTO.getFatFoldBetweenNavelAndLowerBelly())) -
                            (0.00112 * (Math.pow(userConditionWithoutFatPercentFemaleDTO.getFatFoldBetweenShoulderAndElbow(), 2) +
                                    Math.pow(userConditionWithoutFatPercentFemaleDTO.getFatFoldBetweenChestAndIlium(), 2) +
                                    Math.pow(userConditionWithoutFatPercentFemaleDTO.getFatFoldBetweenNavelAndLowerBelly(), 2))) +
                            (0.03661 * userConditionWithoutFatPercentFemaleDTO.getAge())) - 4.03653;
        }
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
