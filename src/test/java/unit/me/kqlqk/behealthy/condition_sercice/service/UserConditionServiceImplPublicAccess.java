package unit.me.kqlqk.behealthy.condition_sercice.service;

import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.repository.UserConditionRepository;
import me.kqlqk.behealthy.condition_service.service.impl.UserConditionServiceImpl;

import javax.validation.Validator;

public class UserConditionServiceImplPublicAccess extends UserConditionServiceImpl {
    public UserConditionServiceImplPublicAccess(UserConditionRepository userConditionRepository, Validator validator) {
        super(userConditionRepository, validator);
    }

    protected double getFetPercentTest(Gender gender,
                                       int fatFoldBetweenChestAndIlium,
                                       int fatFoldBetweenNavelAndLowerBelly,
                                       int fatFoldBetweenNippleAndArmpit,
                                       int fatFoldBetweenNippleAndUpperChest,
                                       int fatFoldBetweenShoulderAndElbow,
                                       int age) {
        return getFatPercent(gender,
                fatFoldBetweenChestAndIlium,
                fatFoldBetweenNavelAndLowerBelly,
                fatFoldBetweenNippleAndArmpit,
                fatFoldBetweenNippleAndUpperChest,
                fatFoldBetweenShoulderAndElbow,
                age);
    }
}
