package integration.me.kqlqk.behealthy.condition_service.controller.rest.v1;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import integration.me.kqlqk.behealthy.condition_service.service.UserPhotoServiceImplTest;
import me.kqlqk.behealthy.condition_service.dto.user_photo.AddUserPhotoDTO;
import me.kqlqk.behealthy.condition_service.model.UserPhoto;
import me.kqlqk.behealthy.condition_service.service.impl.UserPhotoServiceImpl;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class UserPhotoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserPhotoServiceImpl userPhotoService;

    @AfterEach
    public void close(@Value("${photo.dir}") String dir) throws IOException {
        FileUtils.cleanDirectory(new File(dir));
    }

    @Test
    public void getEncodedPhotoByDate_shouldReturnEncodedPhoto() throws Exception {
        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setUserId(1);
        userPhoto.setPhotoDate(new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime());
        userPhoto.setPhotoPath("src/test/resources/tmp_files/1--01-01-23");
        userPhotoService.save(userPhoto, UserPhotoServiceImplTest.encodedPhoto);

        mockMvc.perform(get("/api/v1/photo")
                                .param("userId", "1")
                                .param("date", "01-01-23")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.encodedPhoto").exists());
    }

    @Test
    public void getEncodedPhotoByDate_shouldReturnJsonException() throws Exception {
        mockMvc.perform(get("/api/v1/photo")
                                .param("userId", "1")
                                .param("date", "01-01-23")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("UserPhoto with userId = 1 and date = 01-01-23 not found")));

        mockMvc.perform(get("/api/v1/photo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("date", "01-01-23"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));

        mockMvc.perform(get("/api/v1/photo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'date' for method parameter type String is not present")));
    }

    @Test
    public void getAllPhotosAndFiles_shouldReturnAllPhotosAndFiles() throws Exception {
        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setUserId(1);
        userPhoto.setPhotoDate(new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime());
        userPhoto.setPhotoPath("src/test/resources/tmp_files/1--01-01-23");
        userPhotoService.save(userPhoto, UserPhotoServiceImplTest.encodedPhoto);

        mockMvc.perform(get("/api/v1/photo/all")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].photoPath").exists())
                .andExpect(jsonPath("$[0].photoDate").exists())
                .andExpect(jsonPath("$[0].encodedPhoto").exists());
    }

    @Test
    public void getAllPhotosAndFiles_shouldReturnJsonException() throws Exception {
        mockMvc.perform(get("/api/v1/photo/all")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("UserPhotos with userId = 1 not found")));

        mockMvc.perform(get("/api/v1/photo")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));
    }

    @Test
    public void saveUserPhoto_shouldSaveUserPhoto() throws Exception {
        AddUserPhotoDTO addUserPhotoDTO = new AddUserPhotoDTO("01-01-23", UserPhotoServiceImplTest.encodedPhoto);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(addUserPhotoDTO);

        mockMvc.perform(post("/api/v1/photo")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void saveUserPhoto_shouldReturnJsonWithException() throws Exception {
        AddUserPhotoDTO addUserPhotoDTO = new AddUserPhotoDTO("badFormat", UserPhotoServiceImplTest.encodedPhoto);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(addUserPhotoDTO);

        mockMvc.perform(post("/api/v1/photo")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("You should use the following pattern: 'dd-mm-yy'")));


        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setUserId(1);
        userPhoto.setPhotoDate(new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime());
        userPhoto.setPhotoPath("src/test/resources/tmp_files/1--01-01-23");
        userPhotoService.save(userPhoto, UserPhotoServiceImplTest.encodedPhoto);
        addUserPhotoDTO = new AddUserPhotoDTO("01-01-23", UserPhotoServiceImplTest.encodedPhoto);
        json = mapper.writeValueAsString(addUserPhotoDTO);

        mockMvc.perform(post("/api/v1/photo")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("UserPhoto with userId = 1 and photoDate = 01-01-23 already exists")));


        mockMvc.perform(post("/api/v1/photo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));
    }

}
