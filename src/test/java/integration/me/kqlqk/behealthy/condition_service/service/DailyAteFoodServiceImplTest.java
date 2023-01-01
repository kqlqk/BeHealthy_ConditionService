package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.exception.exceptions.FoodNotFoundException;
import me.kqlqk.behealthy.condition_service.service.impl.DailyAteFoodServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ServiceTest
public class DailyAteFoodServiceImplTest {
    @Autowired
    private DailyAteFoodServiceImpl dailyAteFoodService;


    @Test
    public void add_shouldAddNewDailyAteFoodToUser() {
        int oldDailyFoodSize = dailyAteFoodService.getByUserId(1).size();

        dailyAteFoodService.add(1, "Coca-cola zero", 500, 1, 0, 0, 0.1);

        int newDailyFoodSize = dailyAteFoodService.getByUserId(1).size();

        assertThat(newDailyFoodSize).isEqualTo(oldDailyFoodSize + 1);
    }

    @Test
    public void delete_shouldDeleteFoodFromDailyAteFood() {
        int oldDailyAteFoodSize = dailyAteFoodService.getByUserId(1).size();

        dailyAteFoodService.delete(1, 1);

        int newDailyAteFoodSize = dailyAteFoodService.getByUserId(1).size();

        assertThat(newDailyAteFoodSize).isEqualTo(oldDailyAteFoodSize - 1);
    }

    @Test
    public void delete_shouldThrowException() {
        assertThrows(FoodNotFoundException.class, () -> dailyAteFoodService.delete(0, 0));

        assertThrows(FoodNotFoundException.class, () -> dailyAteFoodService.delete(0, 1));
    }
}