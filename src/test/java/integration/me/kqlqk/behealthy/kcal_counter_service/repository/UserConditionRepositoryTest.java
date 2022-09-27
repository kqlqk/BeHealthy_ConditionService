package integration.me.kqlqk.behealthy.kcal_counter_service.repository;

import annotations.RepositoryTest;
import me.kqlqk.behealthy.kcal_counter_service.model.UserCondition;
import me.kqlqk.behealthy.kcal_counter_service.repository.UserConditionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
public class UserConditionRepositoryTest {
    @Autowired
    private UserConditionRepository userConditionRepository;

    @Test
    public void findById_shouldFindByIdOrReturnNull() {
        UserCondition userCondition = userConditionRepository.findById(1);

        assertThat(userCondition).isNotNull();
        assertThat(userCondition.getId()).isEqualTo(1);

        UserCondition nullUserCondition = userConditionRepository.findById(99);
        assertThat(nullUserCondition).isNull();
    }

    @Test
    public void findByUserId_shouldFindByUserIdOrReturnNull() {
        UserCondition userCondition = userConditionRepository.findByUserId(1);

        assertThat(userCondition).isNotNull();
        assertThat(userCondition.getId()).isEqualTo(1);

        UserCondition nullUserCondition = userConditionRepository.findByUserId(99);
        assertThat(nullUserCondition).isNull();
    }

    @Test
    public void existsByUserId_shouldCheckIfExistsByUserId() {
        assertThat(userConditionRepository.existsByUserId(1)).isTrue();
        assertThat(userConditionRepository.existsByUserId(99)).isFalse();
    }
}
