package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.service.impl.DailyAteFoodServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
public class DailyAteFoodServiceImplTest {
    @Autowired
    private DailyAteFoodServiceImpl dailyFoodService;


    @Test
    public void add_shouldAddNewDailyAteFoodToUser() {
        int oldDailyFoodSize = dailyFoodService.getByUserId(1).size();

        dailyFoodService.add(1, "Coca-cola zero", 500, 1, 0, 0, 0.1);

        int newDailyFoodSize = dailyFoodService.getByUserId(1).size();

        assertThat(newDailyFoodSize).isEqualTo(oldDailyFoodSize + 1);
    }

    @Test
    public void delete_shouldDeleteFoodFromDailyAteFood() {
        int oldDailyFoodSize = dailyFoodService.getByUserId(1).size();

        dailyFoodService.delete(1);

        int newDailyFoodSize = dailyFoodService.getByUserId(1).size();

        assertThat(newDailyFoodSize).isEqualTo(oldDailyFoodSize - 1);
    }
}