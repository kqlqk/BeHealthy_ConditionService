package me.kqlqk.behealthy.condition_service.service;

import me.kqlqk.behealthy.condition_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import org.springframework.stereotype.Service;

@Service
public interface UserConditionService {
    UserCondition getById(long id);

    UserCondition getByUserId(long id);

    boolean existsByUserId(long id);

    void generateAndSaveConditionWithoutFatPercentForMale(UserConditionDTO userConditionDTO,
                                                          int fatFoldBetweenChestAndIlium,
                                                          int fatFoldBetweenNavelAndLowerBelly,
                                                          int fatFoldBetweenNippleAndArmpit,
                                                          int fatFoldBetweenNippleAndUpperChest);

    void generateAndSaveConditionWithoutFatPercentForFemale(UserConditionDTO userConditionDTO,
                                                            int fatFoldBetweenShoulderAndElbow,
                                                            int fatFoldBetweenChestAndIlium,
                                                            int fatFoldBetweenNippleAndArmpit);

    void generateAndSaveCondition(UserConditionDTO userConditionDTO);

    void updateCondition(UserConditionDTO userConditionDTO);
}
