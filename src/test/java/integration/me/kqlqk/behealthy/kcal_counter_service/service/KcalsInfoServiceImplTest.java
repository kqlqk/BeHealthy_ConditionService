package integration.me.kqlqk.behealthy.kcal_counter_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.kcal_counter_service.model.KcalsInfo;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Gender;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Goal;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Intensity;
import me.kqlqk.behealthy.kcal_counter_service.service.impl.KcalsInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
public class KcalsInfoServiceImplTest {
    @Autowired
    private KcalsInfoServiceImpl kcalsInfoService;

    @Test
    public void generateDailyKcals() {
        KcalsInfo kcalsInfo =
                kcalsInfoService.generateDailyKcals(Gender.FEMALE, (byte) 20, (short) 160, (short) 60, Intensity.AVG, Goal.LOSE);

        assertThat(kcalsInfo.getProtein()).isNotNull();
        assertThat(kcalsInfo.getFat()).isNotNull();
        assertThat(kcalsInfo.getCarb()).isNotNull();
    }
}