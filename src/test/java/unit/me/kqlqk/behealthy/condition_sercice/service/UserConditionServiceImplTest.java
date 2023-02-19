package unit.me.kqlqk.behealthy.condition_sercice.service;


import me.kqlqk.behealthy.condition_service.repository.UserConditionRepository;
import me.kqlqk.behealthy.condition_service.service.impl.UserConditionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserConditionServiceImplTest {
    @InjectMocks
    private UserConditionServiceImpl userConditionService;

    @Mock
    private UserConditionRepository userConditionRepository;


    @Test
    public void getFatPercentForMale_shouldReturnFatPercentByMeasurements() {
        double result = userConditionService.generateFatPercentForMale(20, 10, 12, 8, 6);
        assertEquals(9, result, 1);

        result = userConditionService.generateFatPercentForMale(20, 12, 14, 10, 8);
        assertEquals(11, result, 1);

        result = userConditionService.generateFatPercentForMale(20, 14, 16, 12, 10);
        assertEquals(13, result, 1);

        result = userConditionService.generateFatPercentForMale(20, 16, 18, 14, 12);
        assertEquals(15, result, 1);

        result = userConditionService.generateFatPercentForMale(20, 20, 20, 16, 14);
        assertEquals(17, result, 1);
    }

    @Test
    public void getFatPercentForFemale_shouldReturnFatPercentByMeasurements() {
        double result = userConditionService.generateFatPercentForFemale(20, 8, 10, 12);
        assertEquals(9, result, 1);

        result = userConditionService.generateFatPercentForFemale(20, 10, 12, 14);
        assertEquals(11, result, 1);

        result = userConditionService.generateFatPercentForFemale(20, 12, 14, 16);
        assertEquals(13, result, 1);

        result = userConditionService.generateFatPercentForFemale(20, 14, 16, 18);
        assertEquals(15, result, 1);

        result = userConditionService.generateFatPercentForFemale(20, 16, 18, 20);
        assertEquals(18, result, 1);
    }
}


