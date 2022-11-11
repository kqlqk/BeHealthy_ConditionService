package integration.me.kqlqk.behealthy.kcal_counter_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.service.impl.DailyFoodServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
public class DailyFoodServiceImplTest {
    @Autowired
    private DailyFoodServiceImpl dailyFoodService;


    @Test
    public void add_shouldAddNewDailyFoodToUser() {
        assertThat(dailyFoodService.getByUserId(1)).hasSize(2);

        dailyFoodService.add(1, "Coca-cola zero", 500, 1, 0, 0, 0.1);

        assertThat(dailyFoodService.getByUserId(1)).hasSize(3);
    }

    @Test
    public void delete() {
        assertThat(dailyFoodService.getByUserId(1)).hasSize(2);

        dailyFoodService.delete(1);

        assertThat(dailyFoodService.getByUserId(1)).hasSize(1);
    }
}