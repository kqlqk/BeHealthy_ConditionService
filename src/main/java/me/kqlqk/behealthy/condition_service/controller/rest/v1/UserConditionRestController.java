package me.kqlqk.behealthy.condition_service.controller.rest.v1;

import me.kqlqk.behealthy.condition_service.dto.user_condition.AddUpdateUserConditionDTO;
import me.kqlqk.behealthy.condition_service.dto.user_condition.GetUserConditionDTO;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionException;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import me.kqlqk.behealthy.condition_service.service.UserConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class UserConditionRestController {
    private final UserConditionService userConditionService;
    private final Validator validator;

    @Autowired
    public UserConditionRestController(UserConditionService userConditionService, Validator validator) {
        this.userConditionService = userConditionService;
        this.validator = validator;
    }

    @GetMapping("/condition")
    public GetUserConditionDTO getUserConditionByUserId(@RequestParam long userId) {
        return GetUserConditionDTO.convert(userConditionService.getByUserId(userId));
    }

    @PostMapping("/condition")
    public ResponseEntity<?> createUserCondition(@RequestParam long userId, @RequestBody AddUpdateUserConditionDTO createConditionDTO) {
        Set<ConstraintViolation<AddUpdateUserConditionDTO>> constraintViolations = validator.validateProperty(createConditionDTO, "gender");
        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        constraintViolations = validator.validateProperty(createConditionDTO, "age");
        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        constraintViolations = validator.validateProperty(createConditionDTO, "height");
        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        constraintViolations = validator.validateProperty(createConditionDTO, "weight");
        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        constraintViolations = validator.validateProperty(createConditionDTO, "activity");
        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        constraintViolations = validator.validateProperty(createConditionDTO, "goal");
        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        if (createConditionDTO.isFatPercentExists()) {
            constraintViolations = validator.validateProperty(createConditionDTO, "fatPercent");
            if (!constraintViolations.isEmpty()) {
                throw new UserConditionException(constraintViolations.iterator().next().getMessage());
            }
        }
        else {
            switch (createConditionDTO.getGender()) {
                case MALE:
                    constraintViolations = validator.validateProperty(createConditionDTO, "fatFoldBetweenChestAndIlium");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    constraintViolations = validator.validateProperty(createConditionDTO, "fatFoldBetweenNavelAndLowerBelly");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    constraintViolations = validator.validateProperty(createConditionDTO, "fatFoldBetweenNippleAndArmpit");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    constraintViolations = validator.validateProperty(createConditionDTO, "fatFoldBetweenNippleAndUpperChest");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    createConditionDTO.setFatPercent(userConditionService.generateFatPercentForMale(
                            createConditionDTO.getAge(),
                            createConditionDTO.getFatFoldBetweenChestAndIlium(),
                            createConditionDTO.getFatFoldBetweenNavelAndLowerBelly(),
                            createConditionDTO.getFatFoldBetweenNippleAndArmpit(),
                            createConditionDTO.getFatFoldBetweenNippleAndUpperChest()));

                    break;
                case FEMALE:
                    constraintViolations = validator.validateProperty(createConditionDTO, "fatFoldBetweenShoulderAndElbow");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    constraintViolations = validator.validateProperty(createConditionDTO, "fatFoldBetweenChestAndIlium");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    constraintViolations = validator.validateProperty(createConditionDTO, "fatFoldBetweenNavelAndLowerBelly");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    createConditionDTO.setFatPercent(userConditionService.generateFatPercentForFemale(
                            createConditionDTO.getAge(),
                            createConditionDTO.getFatFoldBetweenShoulderAndElbow(),
                            createConditionDTO.getFatFoldBetweenChestAndIlium(),
                            createConditionDTO.getFatFoldBetweenNavelAndLowerBelly()));

                    break;
            }
        }

        UserCondition userCondition = new UserCondition(userId,
                                                        null,
                                                        createConditionDTO.getGender(),
                                                        createConditionDTO.getAge(),
                                                        createConditionDTO.getHeight(),
                                                        createConditionDTO.getWeight(),
                                                        createConditionDTO.getActivity(),
                                                        createConditionDTO.getGoal(),
                                                        createConditionDTO.getFatPercent());

        userConditionService.save(userCondition);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/condition")
    public ResponseEntity<?> updateCondition(@RequestParam long userId, @RequestBody AddUpdateUserConditionDTO updateConditionDTO) {
        Set<ConstraintViolation<AddUpdateUserConditionDTO>> constraintViolations = validator.validateProperty(updateConditionDTO, "gender");
        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        constraintViolations = validator.validateProperty(updateConditionDTO, "age");
        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        constraintViolations = validator.validateProperty(updateConditionDTO, "height");
        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        constraintViolations = validator.validateProperty(updateConditionDTO, "weight");
        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        constraintViolations = validator.validateProperty(updateConditionDTO, "activity");
        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        constraintViolations = validator.validateProperty(updateConditionDTO, "goal");
        if (!constraintViolations.isEmpty()) {
            throw new UserConditionException(constraintViolations.iterator().next().getMessage());
        }

        if (updateConditionDTO.isFatPercentExists()) {
            constraintViolations = validator.validateProperty(updateConditionDTO, "fatPercent");
            if (!constraintViolations.isEmpty()) {
                throw new UserConditionException(constraintViolations.iterator().next().getMessage());
            }
        }
        else {
            switch (updateConditionDTO.getGender()) {
                case MALE:
                    constraintViolations = validator.validateProperty(updateConditionDTO, "fatFoldBetweenChestAndIlium");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    constraintViolations = validator.validateProperty(updateConditionDTO, "fatFoldBetweenNavelAndLowerBelly");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    constraintViolations = validator.validateProperty(updateConditionDTO, "fatFoldBetweenNippleAndArmpit");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    constraintViolations = validator.validateProperty(updateConditionDTO, "fatFoldBetweenNippleAndUpperChest");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    updateConditionDTO.setFatPercent(userConditionService.generateFatPercentForMale(
                            updateConditionDTO.getAge(),
                            updateConditionDTO.getFatFoldBetweenChestAndIlium(),
                            updateConditionDTO.getFatFoldBetweenNavelAndLowerBelly(),
                            updateConditionDTO.getFatFoldBetweenNippleAndArmpit(),
                            updateConditionDTO.getFatFoldBetweenNippleAndUpperChest()));

                    break;
                case FEMALE:
                    constraintViolations = validator.validateProperty(updateConditionDTO, "fatFoldBetweenShoulderAndElbow");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    constraintViolations = validator.validateProperty(updateConditionDTO, "fatFoldBetweenChestAndIlium");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    constraintViolations = validator.validateProperty(updateConditionDTO, "fatFoldBetweenNavelAndLowerBelly");
                    if (!constraintViolations.isEmpty()) {
                        throw new UserConditionException(constraintViolations.iterator().next().getMessage());
                    }

                    updateConditionDTO.setFatPercent(userConditionService.generateFatPercentForFemale(
                            updateConditionDTO.getAge(),
                            updateConditionDTO.getFatFoldBetweenShoulderAndElbow(),
                            updateConditionDTO.getFatFoldBetweenChestAndIlium(),
                            updateConditionDTO.getFatFoldBetweenNavelAndLowerBelly()));

                    break;
            }
        }

        UserCondition userCondition = userConditionService.getByUserId(userId);
        userCondition.setGender(updateConditionDTO.getGender());
        userCondition.setAge(updateConditionDTO.getAge());
        userCondition.setHeight(updateConditionDTO.getHeight());
        userCondition.setWeight(updateConditionDTO.getWeight());
        userCondition.setActivity(updateConditionDTO.getActivity());
        userCondition.setGoal(updateConditionDTO.getGoal());
        userCondition.setFatPercent(updateConditionDTO.getFatPercent());

        userConditionService.update(userCondition);

        return ResponseEntity.ok().build();
    }
}
