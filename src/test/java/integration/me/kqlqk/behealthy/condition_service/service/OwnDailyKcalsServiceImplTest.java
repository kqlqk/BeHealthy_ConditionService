package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.dto.OwnDailyKcalsDTO;
import me.kqlqk.behealthy.condition_service.model.OwnDailyKcals;
import me.kqlqk.behealthy.condition_service.repository.OwnDailyKcalsRepository;
import me.kqlqk.behealthy.condition_service.service.impl.OwnDailyKcalsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ServiceTest
public class OwnDailyKcalsServiceImplTest {
    @Autowired
    private OwnDailyKcalsServiceImpl ownDailyKcalsService;

    @Autowired
    private OwnDailyKcalsRepository ownDailyKcalsRepository;

    @Test
    public void save_shouldSaveOwnDailyKcalsToDb() {
        OwnDailyKcalsDTO ownDailyKcalsDTO = new OwnDailyKcalsDTO(1, 1, 1, 1, 2, true);

        int size = ownDailyKcalsRepository.findAll().size();

        ownDailyKcalsService.save(ownDailyKcalsDTO);

        int newSize = ownDailyKcalsRepository.findAll().size();

        assertThat(newSize).isEqualTo(size + 1);
    }

    @Test
    public void update_shouldUpdateOwnDailyKcalsToDb() {
        OwnDailyKcalsDTO ownDailyKcalsDTO = new OwnDailyKcalsDTO(2, 2, 2, 1, false);

        ownDailyKcalsService.update(ownDailyKcalsDTO);

        OwnDailyKcals ownDailyKcals = ownDailyKcalsService.getByUserId(1);

        assertThat(ownDailyKcals.getProtein()).isEqualTo(2);
        assertThat(ownDailyKcals.getFat()).isEqualTo(2);
        assertThat(ownDailyKcals.getCarb()).isEqualTo(2);
    }

    @Test
    public void changePriority_shouldChangePriorityInDb() {
        OwnDailyKcalsDTO ownDailyKcalsDTO = new OwnDailyKcalsDTO();
        ownDailyKcalsDTO.setUserId(1);
        ownDailyKcalsDTO.setInPriority(false);

        ownDailyKcalsService.update(ownDailyKcalsDTO);

        OwnDailyKcals ownDailyKcals = ownDailyKcalsService.getByUserId(1);

        assertThat(ownDailyKcals.isInPriority()).isEqualTo(false);
    }
}
