package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.exception.exceptions.DailyAteFoodNotFoundException;
import me.kqlqk.behealthy.condition_service.model.DailyAteFood;
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
    public void save_shouldAddNewDailyAteFoodToDb() {
        int userId = 1;

        int oldDailyFoodSize = dailyAteFoodService.getByUserId(userId).size();

        DailyAteFood dailyAteFood = new DailyAteFood();
        dailyAteFood.setUserId(userId);
        dailyAteFood.setName("Food");
        dailyAteFood.setProtein(10);
        dailyAteFood.setFat(3);
        dailyAteFood.setCarb(20);
        dailyAteFood.setWeight(300);
        dailyAteFood.setToday(true);

        dailyAteFoodService.save(dailyAteFood);

        int newDailyFoodSize = dailyAteFoodService.getByUserId(userId).size();

        assertThat(newDailyFoodSize).isEqualTo(oldDailyFoodSize + 1);
    }

    @Test
    public void save_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> dailyAteFoodService.save(null));
    }

    @Test
    public void update_shouldUpdateDailyAteFoodInDb() {
        int userId = 1;

        DailyAteFood dailyAteFood = dailyAteFoodService.getByUserId(userId).get(0);
        dailyAteFood.setName("NewFood");
        dailyAteFood.setProtein(10);
        dailyAteFood.setFat(3);
        dailyAteFood.setCarb(20);
        dailyAteFood.setWeight(300);
        dailyAteFood.setToday(false);

        dailyAteFoodService.update(dailyAteFood);

        DailyAteFood dailyAteFoodFromDb = dailyAteFoodService.getByUserId(userId).get(0);
        assertThat(dailyAteFoodFromDb.getName()).isEqualTo(dailyAteFood.getName());
        assertThat(dailyAteFoodFromDb.getUserId()).isEqualTo(dailyAteFood.getUserId());
    }

    @Test
    public void update_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> dailyAteFoodService.update(null));
    }

    @Test
    public void delete_shouldDeleteFoodFromDb() {
        int oldDailyAteFoodSize = dailyAteFoodService.getByUserId(1).size();

        dailyAteFoodService.delete(1, 1);

        int newDailyAteFoodSize = dailyAteFoodService.getByUserId(1).size();

        assertThat(newDailyAteFoodSize).isEqualTo(oldDailyAteFoodSize - 1);
    }

    @Test
    public void delete_shouldThrowException() {
        assertThrows(DailyAteFoodNotFoundException.class, () -> dailyAteFoodService.delete(0, 0));
        assertThrows(DailyAteFoodNotFoundException.class, () -> dailyAteFoodService.delete(0, 1));
    }
}