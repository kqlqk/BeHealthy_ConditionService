package integration.me.kqlqk.behealthy.condition_service.controller.rest.v1;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.condition_service.dto.UserPhotoDTO;
import me.kqlqk.behealthy.condition_service.repository.UserPhotoRepository;
import me.kqlqk.behealthy.condition_service.service.impl.UserPhotoServiceImpl;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class UserPhotoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserPhotoRepository userPhotoRepository;

    @Autowired
    private UserPhotoServiceImpl userPhotoService;

    @Test
    public void saveUserPhoto_shouldSaveUserPhoto() throws Exception {
        UserPhotoDTO userPhotoDTO = new UserPhotoDTO(1, "someString", "20-11-23");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(userPhotoDTO);

        userPhotoService.setUserPhotoDirectory("src/test/resources/tmp_files/");
        int oldUserPhotoCount = userPhotoRepository.findAll().size();

        mockMvc.perform(post("/api/v1/photo")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());

        int newUserPhotoCount = userPhotoRepository.findAll().size();

        File dir = new File("src/test/resources/tmp_files/");
        FileUtils.cleanDirectory(dir);

        assertThat(newUserPhotoCount).isEqualTo(oldUserPhotoCount + 1);
    }

    @Test
    public void saveUserPhoto_shouldThrowException() throws Exception {
        mockMvc.perform(post("/api/v1/photo")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request body is missing")));
    }

}
