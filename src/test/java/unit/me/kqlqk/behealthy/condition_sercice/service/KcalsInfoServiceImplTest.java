package unit.me.kqlqk.behealthy.condition_sercice.service;

import me.kqlqk.behealthy.condition_service.model.KcalsInfo;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import me.kqlqk.behealthy.condition_service.repository.KcalsInfoRepository;
import me.kqlqk.behealthy.condition_service.service.impl.KcalsInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KcalsInfoServiceImplTest {
    @Mock
    private KcalsInfoRepository kcalsInfoRepository;

    @InjectMocks
    private KcalsInfoServiceImpl kcalsInfoService;


    @Test
    public void generateDailyKcals_shouldGenerateAllPossibleOptions() {
        KcalsInfo maleLose20Fat =
                kcalsInfoService.generateDailyKcals(Gender.MALE, 18, 180, 90, Intensity.MIN, Goal.LOSE, 20);

        assertThat(maleLose20Fat.getProtein()).isGreaterThan(150);
        assertThat(maleLose20Fat.getProtein()).isLessThan(200);
        assertThat(maleLose20Fat.getFat()).isGreaterThan(50);
        assertThat(maleLose20Fat.getFat()).isLessThan(150);
        assertThat(maleLose20Fat.getCarb()).isGreaterThan(80);
        assertThat(maleLose20Fat.getCarb()).isLessThan(200);

        KcalsInfo maleMaintain17Fat =
                kcalsInfoService.generateDailyKcals(Gender.MALE, 18, 180, 76, Intensity.AVG, Goal.MAINTAIN, 17);

        assertThat(maleMaintain17Fat.getProtein()).isGreaterThan(125);
        assertThat(maleMaintain17Fat.getProtein()).isLessThan(200);
        assertThat(maleMaintain17Fat.getFat()).isGreaterThan(50);
        assertThat(maleMaintain17Fat.getFat()).isLessThan(150);
        assertThat(maleMaintain17Fat.getCarb()).isGreaterThan(100);
        assertThat(maleMaintain17Fat.getCarb()).isLessThan(400);


        KcalsInfo maleGain13Fat =
                kcalsInfoService.generateDailyKcals(Gender.MALE, 18, 180, 70, Intensity.AVG, Goal.GAIN, 13);

        assertThat(maleGain13Fat.getProtein()).isGreaterThan(125);
        assertThat(maleGain13Fat.getProtein()).isLessThan(180);
        assertThat(maleGain13Fat.getFat()).isGreaterThan(50);
        assertThat(maleGain13Fat.getFat()).isLessThan(160);
        assertThat(maleGain13Fat.getCarb()).isGreaterThan(200);
        assertThat(maleGain13Fat.getCarb()).isLessThan(500);
    }
}
