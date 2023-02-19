package unit.me.kqlqk.behealthy.condition_sercice.service;

import me.kqlqk.behealthy.condition_service.repository.DailyAteFoodRepository;
import me.kqlqk.behealthy.condition_service.service.impl.DailyAteFoodServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class DailyAteFoodServiceImplTest {
    @InjectMocks
    private DailyAteFoodServiceImpl dailyAteFoodService;

    @Mock
    private DailyAteFoodRepository dailyAteFoodRepository;

    @Test
    public void getKcals_shouldReturnCorrectValue() {
        double weight = 200;
        int protein = 10;
        int fat = 10;
        int carb = 20;
        assertThat(dailyAteFoodService.getKcals(weight, protein, fat, carb)).isEqualTo(
                (int) (weight / 100 * (protein * 4 + fat * 9 + carb * 4)));

        protein = 0;
        fat = 0;
        carb = 20;
        assertThat(dailyAteFoodService.getKcals(weight, protein, fat, carb)).isEqualTo(
                (int) (weight / 100 * (carb * 4)));

        protein = 10;
        fat = 0;
        carb = 0;
        assertThat(dailyAteFoodService.getKcals(weight, protein, fat, carb)).isEqualTo(
                (int) (weight / 100 * (protein * 4)));

        protein = 0;
        fat = 10;
        carb = 0;
        assertThat(dailyAteFoodService.getKcals(weight, protein, fat, carb)).isEqualTo(
                (int) (weight / 100 * (fat * 9)));

        protein = 0;
        fat = 10;
        carb = 20;
        assertThat(dailyAteFoodService.getKcals(weight, protein, fat, carb)).isEqualTo(
                (int) (weight / 100 * (fat * 9 + carb * 4)));

        protein = 10;
        fat = 0;
        carb = 20;
        assertThat(dailyAteFoodService.getKcals(weight, protein, fat, carb)).isEqualTo(
                (int) (weight / 100 * (protein * 4 + carb * 4)));

        protein = 10;
        fat = 10;
        carb = 0;
        assertThat(dailyAteFoodService.getKcals(weight, protein, fat, carb)).isEqualTo(
                (int) (weight / 100 * (protein * 4 + fat * 9)));
    }
}
