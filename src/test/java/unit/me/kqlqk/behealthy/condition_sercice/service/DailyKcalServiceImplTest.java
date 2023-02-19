package unit.me.kqlqk.behealthy.condition_sercice.service;

import me.kqlqk.behealthy.condition_service.model.DailyKcal;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import me.kqlqk.behealthy.condition_service.service.impl.DailyKcalServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DailyKcalServiceImplTest {
    private final DailyKcalServiceImpl dailyKcalsService = new DailyKcalServiceImpl();

    @Test
    public void generateDailyKcals_shouldGenerateAllPossibleOptions() {
        DailyKcal maleLose20Fat = dailyKcalsService.generateDailyKcals(90, 20, Intensity.MIN, Goal.LOSE);

        assertThat(maleLose20Fat.getProtein()).isGreaterThan(150);
        assertThat(maleLose20Fat.getProtein()).isLessThan(200);
        assertThat(maleLose20Fat.getFat()).isGreaterThan(50);
        assertThat(maleLose20Fat.getFat()).isLessThan(150);
        assertThat(maleLose20Fat.getCarb()).isGreaterThan(80);
        assertThat(maleLose20Fat.getCarb()).isLessThan(200);


        DailyKcal maleMaintain17Fat = dailyKcalsService.generateDailyKcals(76, 17, Intensity.AVG, Goal.MAINTAIN);

        assertThat(maleMaintain17Fat.getProtein()).isGreaterThan(125);
        assertThat(maleMaintain17Fat.getProtein()).isLessThan(200);
        assertThat(maleMaintain17Fat.getFat()).isGreaterThan(50);
        assertThat(maleMaintain17Fat.getFat()).isLessThan(150);
        assertThat(maleMaintain17Fat.getCarb()).isGreaterThan(100);
        assertThat(maleMaintain17Fat.getCarb()).isLessThan(400);


        DailyKcal maleGain13Fat = dailyKcalsService.generateDailyKcals(70, 13, Intensity.AVG, Goal.GAIN);

        assertThat(maleGain13Fat.getProtein()).isGreaterThan(125);
        assertThat(maleGain13Fat.getProtein()).isLessThan(180);
        assertThat(maleGain13Fat.getFat()).isGreaterThan(50);
        assertThat(maleGain13Fat.getFat()).isLessThan(160);
        assertThat(maleGain13Fat.getCarb()).isGreaterThan(200);
        assertThat(maleGain13Fat.getCarb()).isLessThan(500);
    }
}
