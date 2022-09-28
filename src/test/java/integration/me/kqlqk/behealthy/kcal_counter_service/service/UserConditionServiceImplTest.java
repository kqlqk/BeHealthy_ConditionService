package integration.me.kqlqk.behealthy.kcal_counter_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Gender;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Goal;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Intensity;
import me.kqlqk.behealthy.kcal_counter_service.repository.KcalsInfoRepository;
import me.kqlqk.behealthy.kcal_counter_service.repository.UserConditionRepository;
import me.kqlqk.behealthy.kcal_counter_service.service.impl.UserConditionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
public class UserConditionServiceImplTest {
    @Autowired
    private UserConditionServiceImpl userConditionService;

    @Autowired
    private UserConditionRepository userConditionRepository;

    @Autowired
    private KcalsInfoRepository kcalsInfoRepository;

    @Test
    public void generateAndSaveCondition_shouldGenerateAndSaveToDbUserCondition() {
        byte userConditionCount = (byte) userConditionRepository.findAll().size();
        byte kcalsInfoCount = (byte) kcalsInfoRepository.findAll().size();
        userConditionService.generateAndSaveCondition(4L, Gender.MALE, (byte) 20, (short) 180, (short) 90, Intensity.AVG, Goal.MAINTAIN);

        assertThat(userConditionRepository.findAll().size()).isEqualTo(userConditionCount + 1);
        assertThat(kcalsInfoRepository.findAll().size()).isEqualTo(kcalsInfoCount + 1);
    }
}
