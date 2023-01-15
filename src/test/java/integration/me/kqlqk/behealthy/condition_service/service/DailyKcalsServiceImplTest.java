package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.condition_service.exception.exceptions.KcalsException;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import me.kqlqk.behealthy.condition_service.service.impl.DailyKcalsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ServiceTest
public class DailyKcalsServiceImplTest {
    @Autowired
    private DailyKcalsServiceImpl kcalsInfoService;

    @Test
    public void generateDailyKcals_shouldThrowException() {
        int invalidWeight = 170;
        UserConditionDTO userConditionDTO3 = new UserConditionDTO(10, Gender.MALE, 183, 183, invalidWeight, Intensity.AVG, Goal.LOSE, 20);

        assertThrows(KcalsException.class, () -> kcalsInfoService.generateDailyKcals(userConditionDTO3));


        int invalidFatPercent = 70;
        UserConditionDTO userConditionDTO4 = new UserConditionDTO(10, Gender.MALE, 183, 183, 85, Intensity.AVG, Goal.LOSE, invalidFatPercent);

        assertThrows(KcalsException.class, () -> kcalsInfoService.generateDailyKcals(userConditionDTO4));


        UserConditionDTO userConditionDTO5 = new UserConditionDTO(10, Gender.MALE, 183, 183, 85, null, Goal.LOSE, invalidFatPercent);

        assertThrows(KcalsException.class, () -> kcalsInfoService.generateDailyKcals(userConditionDTO5));


        UserConditionDTO userConditionDTO6 = new UserConditionDTO(10, Gender.MALE, 183, 183, 85, Intensity.AVG, null, invalidFatPercent);

        assertThrows(KcalsException.class, () -> kcalsInfoService.generateDailyKcals(userConditionDTO6));
    }
}