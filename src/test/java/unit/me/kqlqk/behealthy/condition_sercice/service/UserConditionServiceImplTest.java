package unit.me.kqlqk.behealthy.condition_sercice.service;


import me.kqlqk.behealthy.condition_service.dto.UserConditionWithoutFatPercentFemaleDTO;
import me.kqlqk.behealthy.condition_service.dto.UserConditionWithoutFatPercentMaleDTO;
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
    public void getFatPercent_shouldReturnFatPercentByMeasurements() {
        UserConditionWithoutFatPercentMaleDTO userConditionWithoutFatPercentMaleDTO = new UserConditionWithoutFatPercentMaleDTO(
                1, 17, 0, 0, null, null, 25, 15, 20, 5);
        double fatPercent = userConditionService.getFetPercentTest(userConditionWithoutFatPercentMaleDTO, null);

        assertThat(fatPercent).isBetween(15.0, 17.0);


        UserConditionWithoutFatPercentMaleDTO userConditionWithoutFatPercentMaleDTO2 = new UserConditionWithoutFatPercentMaleDTO(
                1, 40, 0, 0, null, null, 40, 30, 25, 10);
        fatPercent = userConditionService.getFetPercentTest(userConditionWithoutFatPercentMaleDTO2, null);

        assertThat(fatPercent).isBetween(26.0, 30.0);


        UserConditionWithoutFatPercentMaleDTO userConditionWithoutFatPercentMaleDTO3 = new UserConditionWithoutFatPercentMaleDTO(
                1, 25, 0, 0, null, null, 15, 9, 10, 4);
        fatPercent = userConditionService.getFetPercentTest(userConditionWithoutFatPercentMaleDTO3, null);

        assertThat(fatPercent).isBetween(9.0, 11.0);


        UserConditionWithoutFatPercentFemaleDTO userConditionWithoutFatPercentFemaleDTO = new UserConditionWithoutFatPercentFemaleDTO(
                1, 22, 0, 0, null, null, 10, 25, 20);
        fatPercent = userConditionService.getFetPercentTest(null, userConditionWithoutFatPercentFemaleDTO);

        assertThat(fatPercent).isBetween(17.0, 19.0);


        UserConditionWithoutFatPercentFemaleDTO userConditionWithoutFatPercentFemaleDTO2 = new UserConditionWithoutFatPercentFemaleDTO(
                1, 36, 0, 0, null, null, 15, 40, 30);
        fatPercent = userConditionService.getFetPercentTest(null, userConditionWithoutFatPercentFemaleDTO2);

        assertThat(fatPercent).isBetween(28.0, 31.0);


        UserConditionWithoutFatPercentFemaleDTO userConditionWithoutFatPercentFemaleDTO3 = new UserConditionWithoutFatPercentFemaleDTO(
                1, 25, 0, 0, null, null, 5, 20, 10);
        fatPercent = userConditionService.getFetPercentTest(null, userConditionWithoutFatPercentFemaleDTO3);

        assertThat(fatPercent).isBetween(9.0, 11.0);
    }
}


