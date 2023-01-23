package unit.me.kqlqk.behealthy.condition_sercice.service;

import me.kqlqk.behealthy.condition_service.dto.UserConditionWithoutFatPercentFemaleDTO;
import me.kqlqk.behealthy.condition_service.dto.UserConditionWithoutFatPercentMaleDTO;
import me.kqlqk.behealthy.condition_service.repository.UserConditionRepository;
import me.kqlqk.behealthy.condition_service.service.impl.UserConditionServiceImpl;

import javax.validation.Validator;

public class UserConditionServiceImplPublicAccess extends UserConditionServiceImpl {
    public UserConditionServiceImplPublicAccess(UserConditionRepository userConditionRepository, Validator validator) {
        super(userConditionRepository, validator);
    }

    protected double getFetPercentTest(UserConditionWithoutFatPercentMaleDTO userConditionWithoutFatPercentMaleDTO,
                                       UserConditionWithoutFatPercentFemaleDTO userConditionWithoutFatPercentFemaleDTO) {
        return getFatPercent(userConditionWithoutFatPercentMaleDTO, userConditionWithoutFatPercentFemaleDTO);
    }
}
