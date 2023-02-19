package unit.me.kqlqk.behealthy.condition_sercice.service;

import me.kqlqk.behealthy.condition_service.exception.exceptions.UserPhotoException;
import me.kqlqk.behealthy.condition_service.repository.UserPhotoRepository;
import me.kqlqk.behealthy.condition_service.service.impl.UserPhotoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserPhotoServiceImplTest {
    @InjectMocks
    private UserPhotoServiceImpl userPhotoService;

    @Mock
    private UserPhotoRepository userPhotoRepository;


    @Test
    public void convertStringToDate_shouldConvertStringToDate() {
        Date date = userPhotoService.convertStringToDate("01-01-23");
        assertThat(date.getTime()).isEqualTo(new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime().getTime());

        date = userPhotoService.convertStringToDate("12-11-24");
        assertThat(date.getTime()).isEqualTo(new GregorianCalendar(2024, Calendar.NOVEMBER, 12).getTime().getTime());


        date = userPhotoService.convertStringToDate("12-12-25");
        assertThat(date.getTime()).isEqualTo(new GregorianCalendar(2025, Calendar.DECEMBER, 12).getTime().getTime());
    }

    @Test
    public void convertStringToDate_shouldThrowException() {
        assertThrows(UserPhotoException.class, () -> userPhotoService.convertStringToDate("bad-format"));
    }
}
