package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.model.DailyKcals;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import me.kqlqk.behealthy.condition_service.repository.DailyKcalsRepository;
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
    private DailyKcalsRepository dailyKcalsRepository;

    @Test
    public void generateAndSaveCondition_shouldGenerateAndSaveToDbUserCondition() {
        int userConditionCount = (byte) userConditionRepository.findAll().size();
        int kcalsInfoCount = (byte) dailyKcalsRepository.findAll().size();

        userConditionService.generateAndSaveCondition(4L, Gender.MALE, 20, 180, 90, Intensity.AVG, Goal.MAINTAIN, 14.3);

        assertThat(userConditionRepository.findAll().size()).isEqualTo(userConditionCount + 1);
        assertThat(dailyKcalsRepository.findAll().size()).isEqualTo(kcalsInfoCount + 1);
    }

    @Test
    public void updateCondition_shouldUpdateUserConditionAndKcalsInfo() {
        UserCondition oldUserCondition = userConditionService.getByUserId(1);
        DailyKcals oldDailyKcals = oldUserCondition.getDailyKcals();

        userConditionService.updateCondition(
                oldUserCondition.getUserId(), Gender.FEMALE, 30, 160, 50, Intensity.MIN, Goal.GAIN, 13.4);

        UserCondition newUserCondition = userConditionService.getByUserId(1);
        DailyKcals newDailyKcals = newUserCondition.getDailyKcals();

        assertThat(oldUserCondition.getId()).isEqualTo(newUserCondition.getId());
        assertThat(oldUserCondition.getUserId()).isEqualTo(newUserCondition.getUserId());
        assertThat(oldUserCondition.getAge()).isNotEqualTo(newUserCondition.getAge());
        assertThat(oldUserCondition.getHeight()).isNotEqualTo(newUserCondition.getHeight());
        assertThat(oldUserCondition.getGender()).isNotEqualByComparingTo(newUserCondition.getGender());
        assertThat(oldUserCondition.getAge()).isNotEqualTo(newUserCondition.getAge());
        assertThat(oldUserCondition.getHeight()).isNotEqualTo(newUserCondition.getHeight());
        assertThat(oldUserCondition.getWeight()).isNotEqualByComparingTo(newUserCondition.getWeight());
        assertThat(oldUserCondition.getIntensity()).isNotEqualByComparingTo(newUserCondition.getIntensity());
        assertThat(oldUserCondition.getGoal()).isNotEqualByComparingTo(newUserCondition.getGoal());

        assertThat(oldDailyKcals.getId()).isEqualTo(newDailyKcals.getId());
        assertThat(oldDailyKcals.getProtein()).isNotEqualTo(newDailyKcals.getProtein());
        assertThat(oldDailyKcals.getFat()).isNotEqualTo(newDailyKcals.getFat());
        assertThat(oldDailyKcals.getCarb()).isNotEqualTo(newDailyKcals.getCarb());
    }
}
