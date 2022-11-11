package integration.me.kqlqk.behealthy.kcal_counter_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import me.kqlqk.behealthy.condition_service.repository.KcalsInfoRepository;
import me.kqlqk.behealthy.condition_service.repository.UserConditionRepository;
import me.kqlqk.behealthy.condition_service.service.impl.UserConditionServiceImpl;
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

    @Test
    public void updateCondition_shouldUpdateUserConditionAndKcalsInfo() {
        UserCondition oldUserCondition = userConditionService.getByUserId(1);

        userConditionService.updateCondition(
                oldUserCondition.getUserId(), Gender.FEMALE, (byte) 30, (short) 160, (short) 50, Intensity.MIN, Goal.GAIN);

        UserCondition newUserCondition = userConditionService.getByUserId(1);

        assertThat(oldUserCondition.getUserId()).isEqualTo(newUserCondition.getUserId());
        assertThat(oldUserCondition.getAge()).isNotEqualTo(newUserCondition.getAge());
        assertThat(oldUserCondition.getHeight()).isNotEqualTo(newUserCondition.getHeight());
        assertThat(oldUserCondition.getGender()).isNotEqualByComparingTo(newUserCondition.getGender());
        assertThat(oldUserCondition.getAge()).isNotEqualTo(newUserCondition.getAge());
        assertThat(oldUserCondition.getHeight()).isNotEqualTo(newUserCondition.getHeight());
        assertThat(oldUserCondition.getWeight()).isNotEqualByComparingTo(newUserCondition.getWeight());
        assertThat(oldUserCondition.getIntensity()).isNotEqualByComparingTo(newUserCondition.getIntensity());
        assertThat(oldUserCondition.getGoal()).isNotEqualByComparingTo(newUserCondition.getGoal());
    }
}
