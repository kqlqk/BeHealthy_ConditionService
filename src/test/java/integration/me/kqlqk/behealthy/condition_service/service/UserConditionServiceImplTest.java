package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        int userConditionCount = userConditionRepository.findAll().size();
        int kcalsInfoCount = dailyKcalsRepository.findAll().size();

        UserConditionDTO userConditionDTO = new UserConditionDTO(10, Gender.MALE, 20, 180, 90, Intensity.AVG, Goal.MAINTAIN, 14.3);
        userConditionService.generateAndSaveCondition(userConditionDTO);

        assertThat(userConditionRepository.findAll().size()).isEqualTo(userConditionCount + 1);
        assertThat(dailyKcalsRepository.findAll().size()).isEqualTo(kcalsInfoCount + 1);
    }

    @Test
    public void generateAndSaveCondition_shouldThrowException() {
        int invalidAge = 90;
        UserConditionDTO userConditionDTO = new UserConditionDTO(10, Gender.MALE, invalidAge, 183, 85, Intensity.AVG, Goal.LOSE, 20);

        assertThrows(UserConditionException.class, () -> userConditionService.generateAndSaveCondition(userConditionDTO));


        int invalidHeight = 250;
        UserConditionDTO userConditionDTO2 = new UserConditionDTO(10, Gender.MALE, 20, invalidHeight, 85, Intensity.AVG, Goal.LOSE, 20);

        assertThrows(UserConditionException.class, () -> userConditionService.generateAndSaveCondition(userConditionDTO2));


        int invalidWeight = 170;
        UserConditionDTO userConditionDTO3 = new UserConditionDTO(10, Gender.MALE, 183, 183, invalidWeight, Intensity.AVG, Goal.LOSE, 20);

        assertThrows(UserConditionException.class, () -> userConditionService.generateAndSaveCondition(userConditionDTO3));


        int invalidFatPercent = 70;
        UserConditionDTO userConditionDTO4 = new UserConditionDTO(10, Gender.MALE, 183, 183, 85, Intensity.AVG, Goal.LOSE, invalidFatPercent);

        assertThrows(UserConditionException.class, () -> userConditionService.generateAndSaveCondition(userConditionDTO4));
    }

    @Test
    public void updateCondition_shouldUpdateUserConditionAndKcalsInfo() {
        UserCondition oldUserCondition = userConditionService.getByUserId(1);
        DailyKcals oldDailyKcals = oldUserCondition.getDailyKcals();

        UserConditionDTO userConditionDTO = new UserConditionDTO(1, Gender.FEMALE, 30, 160, 50, Intensity.MIN, Goal.GAIN, 13.4);
        userConditionService.updateCondition(userConditionDTO);

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

    @Test
    public void updateCondition_shouldThrowException() {
        int invalidAge = 90;
        UserConditionDTO userConditionDTO = new UserConditionDTO(1, Gender.MALE, invalidAge, 183, 85, Intensity.AVG, Goal.LOSE, 20);

        assertThrows(UserConditionException.class, () -> userConditionService.updateCondition(userConditionDTO));


        int invalidHeight = 250;
        UserConditionDTO userConditionDTO2 = new UserConditionDTO(1, Gender.MALE, 20, invalidHeight, 85, Intensity.AVG, Goal.LOSE, 20);

        assertThrows(UserConditionException.class, () -> userConditionService.updateCondition(userConditionDTO2));


        int invalidWeight = 170;
        UserConditionDTO userConditionDTO3 = new UserConditionDTO(1, Gender.MALE, 183, 183, invalidWeight, Intensity.AVG, Goal.LOSE, 20);

        assertThrows(UserConditionException.class, () -> userConditionService.updateCondition(userConditionDTO3));


        int invalidFatPercent = 70;
        UserConditionDTO userConditionDTO4 = new UserConditionDTO(1, Gender.MALE, 183, 183, 85, Intensity.AVG, Goal.LOSE, invalidFatPercent);

        assertThrows(UserConditionException.class, () -> userConditionService.updateCondition(userConditionDTO4));
    }
}
