package integration.me.kqlqk.behealthy.condition_service.repository;

import annotations.RepositoryTest;
import me.kqlqk.behealthy.condition_service.model.DailyFood;
import me.kqlqk.behealthy.condition_service.repository.DailyFoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
public class DailyFoodRepositoryTest {
    @Autowired
    private DailyFoodRepository dailyFoodRepository;

    @Test
    public void findById_shouldFindByIdOrReturnNull() {
        DailyFood dailyFood = dailyFoodRepository.findById(1);

        assertThat(dailyFood).isNotNull();
        assertThat(dailyFood.getId()).isEqualTo(1);

        DailyFood nullDailyFood = dailyFoodRepository.findById(99);
        assertThat(nullDailyFood).isNull();
    }

    @Test
    public void findByUserId_shouldFindByUserIdOrReturnNull() {
        List<DailyFood> dailyFoods = dailyFoodRepository.findByUserId(1);

        assertThat(dailyFoods).isNotEmpty();
        assertThat(dailyFoods).hasSize(2);

        List<DailyFood> nullDailyFood = dailyFoodRepository.findByUserId(99);
        assertThat(nullDailyFood).isEmpty();
    }
}
