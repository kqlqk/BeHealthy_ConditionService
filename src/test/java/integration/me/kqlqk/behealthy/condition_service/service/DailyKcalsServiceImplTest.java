package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.model.DailyKcals;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import me.kqlqk.behealthy.condition_service.service.impl.DailyKcalsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
public class DailyKcalsServiceImplTest {
    @Autowired
    private DailyKcalsServiceImpl kcalsInfoService;

    @Test
    public void generateDailyKcals_shouldGenerateDailyKcals() {
        DailyKcals dailyKcals =
                kcalsInfoService.generateDailyKcals(Gender.MALE, 20, 183, 85, Intensity.AVG, Goal.LOSE, 20);

        assertThat(dailyKcals.getProtein()).isNotNull();
        assertThat(dailyKcals.getFat()).isNotNull();
        assertThat(dailyKcals.getCarb()).isNotNull();
    }
}