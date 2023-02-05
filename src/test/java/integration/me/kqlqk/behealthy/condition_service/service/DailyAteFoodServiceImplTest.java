package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.dto.DailyAteFoodDTO;
import me.kqlqk.behealthy.condition_service.exception.exceptions.FoodException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.FoodNotFoundException;
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
    public void add_shouldAddNewDailyAteFoodToUser() {
        int userId = 3;

        int oldDailyFoodSize = dailyAteFoodService.getByUserId(userId).size();

        dailyAteFoodService.add(new DailyAteFoodDTO(userId, "Chicken", 200, 20, 0, 0));

        int newDailyFoodSize = dailyAteFoodService.getByUserId(userId).size();
        DailyAteFood dailyAteFood = dailyAteFoodService.getByUserId(userId).get(0);


        assertThat(newDailyFoodSize).isEqualTo(oldDailyFoodSize + 1);
        assertThat(dailyAteFood.getKcals()).isEqualTo(2 * 20 * 4); // weight / 100g * proteins * 4
    }

    @Test
    public void add_shouldThrowException() {
        String invalidProductName = " ";
        DailyAteFoodDTO dailyAteFoodDTO = new DailyAteFoodDTO(1, invalidProductName, 1, 1, 1, 1);

        assertThrows(FoodException.class, () -> dailyAteFoodService.add(dailyAteFoodDTO));


        double invalidWeight = 10000;
        DailyAteFoodDTO dailyAteFoodDTO2 = new DailyAteFoodDTO(1, "product", invalidWeight, 1, 1, 1);

        assertThrows(FoodException.class, () -> dailyAteFoodService.add(dailyAteFoodDTO2));


        double invalidProteins = 1000;
        DailyAteFoodDTO dailyAteFoodDTO4 = new DailyAteFoodDTO(1, "product", 1, invalidProteins, 1, 1);

        assertThrows(FoodException.class, () -> dailyAteFoodService.add(dailyAteFoodDTO4));


        double invalidFats = 1000;
        DailyAteFoodDTO dailyAteFoodDTO5 = new DailyAteFoodDTO(1, "product", 1, 1, invalidFats, 1);

        assertThrows(FoodException.class, () -> dailyAteFoodService.add(dailyAteFoodDTO5));


        double invalidCarbs = 1000;
        DailyAteFoodDTO dailyAteFoodDTO6 = new DailyAteFoodDTO(1, "product", invalidWeight, 1, 1, invalidCarbs);

        assertThrows(FoodException.class, () -> dailyAteFoodService.add(dailyAteFoodDTO6));
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