package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionAlreadyExistsException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserConditionNotFoundException;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import me.kqlqk.behealthy.condition_service.model.enums.Activity;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
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


    @Test
    public void save_shouldSaveUserConditionToDb() {
        int oldUserConditionSize = userConditionRepository.findAll().size();

        UserCondition userCondition =
                new UserCondition(3, null, Gender.MALE, 20, 180, 80, Activity.MAX, Goal.MAINTAIN, 15);
        userConditionService.save(userCondition);

        int newUserConditionSize = userConditionRepository.findAll().size();

        assertThat(newUserConditionSize).isEqualTo(oldUserConditionSize + 1);
    }

    @Test
    public void save_shouldThrowException() {
        UserCondition userCondition =
                new UserCondition(1, null, Gender.MALE, 20, 180, 80, Activity.MAX, Goal.MAINTAIN, 15);

        assertThrows(UserConditionAlreadyExistsException.class, () -> userConditionService.save(userCondition));
    }

    @Test
    public void update_shouldUpdateUserConditionInDb() {
        UserCondition userCondition = userConditionService.getByUserId(1);
        userCondition.setGoal(Goal.GAIN);
        userCondition.setWeight(77);

        userConditionService.update(userCondition);

        assertThat(userConditionService.getByUserId(1).getGoal()).isEqualTo(userCondition.getGoal());
        assertThat(userConditionService.getByUserId(1).getWeight()).isEqualTo(userCondition.getWeight());
        assertThat(userConditionService.getByUserId(1).getDailyKcal()).isEqualTo(userCondition.getDailyKcal());
    }

    @Test
    public void update_shouldThrowException() {
        UserCondition userCondition = new UserCondition();
        userCondition.setId(0);

        assertThrows(UserConditionNotFoundException.class, () -> userConditionService.update(userCondition));
    }
}
