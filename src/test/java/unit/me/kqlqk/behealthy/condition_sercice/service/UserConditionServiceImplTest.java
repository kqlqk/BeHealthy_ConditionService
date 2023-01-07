package unit.me.kqlqk.behealthy.condition_sercice.service;


import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.repository.UserConditionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserConditionServiceImplTest {
    @InjectMocks
    private UserConditionServiceImplPublicAccess userConditionService;

    @Mock
    private UserConditionRepository userConditionRepository;

    @Mock
    private Validator validator;

    @Test
    public void getFetPercent_shouldReturnFatPercentByMeasurements() {
        double fatPercent = userConditionService.getFetPercentTest(Gender.MALE,
                25,
                15,
                20,
                5,
                0,
                17);

        assertThat(fatPercent).isBetween(15.0, 17.0);

        fatPercent = userConditionService.getFetPercentTest(Gender.MALE,
                40,
                30,
                25,
                10,
                0,
                40);

        assertThat(fatPercent).isBetween(26.0, 30.0);

        fatPercent = userConditionService.getFetPercentTest(Gender.MALE,
                15,
                9,
                10,
                4,
                0,
                25);

        assertThat(fatPercent).isBetween(9.0, 11.0);


        fatPercent = userConditionService.getFetPercentTest(Gender.FEMALE,
                25,
                20,
                0,
                0,
                10,
                22);

        assertThat(fatPercent).isBetween(17.0, 19.0);

        fatPercent = userConditionService.getFetPercentTest(Gender.FEMALE,
                40,
                30,
                0,
                0,
                15,
                36);

        assertThat(fatPercent).isBetween(28.0, 31.0);

        fatPercent = userConditionService.getFetPercentTest(Gender.FEMALE,
                20,
                10,
                0,
                0,
                5,
                25);

        assertThat(fatPercent).isBetween(9.0, 11.0);
    }
}


