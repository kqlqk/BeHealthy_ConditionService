package integration.me.kqlqk.behealthy.kcal_counter_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.kcal_counter_service.dto.KcalsInfoDTO;
import me.kqlqk.behealthy.kcal_counter_service.model.Intensity;
import me.kqlqk.behealthy.kcal_counter_service.model.KcalsInfo;
import me.kqlqk.behealthy.kcal_counter_service.repository.KcalsInfoRepository;
import me.kqlqk.behealthy.kcal_counter_service.service.impl.UserConditionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
public class UserConditionServiceImplTest {
    @Autowired
    private UserConditionServiceImpl userConditionService;


    @Test
    public void createCondition_shouldCreateCondition() {
        KcalsInfo kcalsInfo = new KcalsInfo();
        kcalsInfo.setProtein((short) 100);
        kcalsInfo.setFat((short) 60);
        kcalsInfo.setCarb((short) 150);

        userConditionService.createCondition(kcalsInfo, (short) 80, Intensity.MAX, 4);

        assertThat(userConditionService.existsByUserId(4)).isTrue();
    }
}
