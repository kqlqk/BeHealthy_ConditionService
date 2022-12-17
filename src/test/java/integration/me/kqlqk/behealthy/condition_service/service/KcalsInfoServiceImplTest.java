package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.model.KcalsInfo;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import me.kqlqk.behealthy.condition_service.service.impl.KcalsInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
public class KcalsInfoServiceImplTest {
    @Autowired
    private KcalsInfoServiceImpl kcalsInfoService;

    @Test
    public void generateDailyKcals_shouldGenerateDailyKcals() {
        KcalsInfo kcalsInfo =
                kcalsInfoService.generateDailyKcals(Gender.MALE, 20, 183, 85, Intensity.AVG, Goal.LOSE, 20);

        assertThat(kcalsInfo.getProtein()).isNotNull();
        assertThat(kcalsInfo.getFat()).isNotNull();
        assertThat(kcalsInfo.getCarb()).isNotNull();
    }
}