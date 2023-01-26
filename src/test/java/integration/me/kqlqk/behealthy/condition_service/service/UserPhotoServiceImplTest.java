package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.dto.UserPhotoDTO;
import me.kqlqk.behealthy.condition_service.repository.UserPhotoRepository;
import me.kqlqk.behealthy.condition_service.service.impl.UserPhotoServiceImpl;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ServiceTest
public class UserPhotoServiceImplTest {
    @Autowired
    private UserPhotoServiceImpl userPhotoService;

    @Autowired
    private UserPhotoRepository userPhotoRepository;


    @Test
    public void savePhoto_shouldSavePhotoToDB() throws IOException {
        userPhotoService.setUserPhotoDirectory("src/test/resources/tmp_files/");

        int oldUserPhotoCount = userPhotoRepository.findAll().size();

        UserPhotoDTO userPhotoDTO = new UserPhotoDTO(1, "someString", "02-01-23");
        userPhotoService.savePhoto(userPhotoDTO);

        int newUserPhotoCount = userPhotoRepository.findAll().size();

        File dir = new File("src/test/resources/tmp_files/");
        FileUtils.cleanDirectory(dir);

        assertThat(newUserPhotoCount).isEqualTo(oldUserPhotoCount + 1);
    }
}
