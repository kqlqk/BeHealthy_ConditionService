package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserKcalAlreadyExistsException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserKcalNotFoundException;
import me.kqlqk.behealthy.condition_service.model.UserKcal;
import me.kqlqk.behealthy.condition_service.repository.UserKcalRepository;
import me.kqlqk.behealthy.condition_service.service.impl.UserKcalServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ServiceTest
public class UserKcalServiceImplTest {
    @Autowired
    private UserKcalServiceImpl userKcalService;

    @Autowired
    private UserKcalRepository userKcalRepository;

    @Test
    public void save_shouldSaveUserKcalToDb() {
        int oldSize = userKcalRepository.findAll().size();

        UserKcal userKcal = new UserKcal();
        userKcal.setProtein(2);
        userKcal.setFat(2);
        userKcal.setCarb(2);
        userKcal.setKcal(34);
        userKcal.setInPriority(true);
        userKcal.setOnlyKcal(false);
        userKcal.setUserId(2);

        userKcalService.save(userKcal);

        int newSize = userKcalRepository.findAll().size();

        assertThat(newSize).isEqualTo(oldSize + 1);
    }

    @Test
    public void save_shouldThrowException() {
        UserKcal userKcal = userKcalService.getByUserId(1);
        userKcal.setOnlyKcal(true);

        assertThrows(UserKcalAlreadyExistsException.class, () -> userKcalService.save(userKcal));
    }

    @Test
    public void update_shouldUpdateUserKcalInDb() {
        UserKcal userKcal = userKcalService.getByUserId(1);
        userKcal.setProtein(2);
        userKcal.setFat(2);
        userKcal.setCarb(2);
        userKcal.setKcal(34);

        userKcalService.update(userKcal);

        assertThat(userKcalService.getByUserId(1).getProtein()).isEqualTo(userKcal.getProtein());
        assertThat(userKcalService.getByUserId(1).getFat()).isEqualTo(userKcal.getFat());
        assertThat(userKcalService.getByUserId(1).getCarb()).isEqualTo(userKcal.getCarb());
        assertThat(userKcalService.getByUserId(1).getKcal()).isEqualTo(userKcal.getKcal());
    }

    @Test
    public void update_shouldThrowException() {
        UserKcal userKcal = new UserKcal();

        assertThrows(UserKcalNotFoundException.class, () -> userKcalService.update(userKcal));
    }
}
