package integration.me.kqlqk.behealthy.condition_service.controller.rest.v1;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.condition_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.condition_service.model.UserCondition;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;
import me.kqlqk.behealthy.condition_service.repository.KcalsInfoRepository;
import me.kqlqk.behealthy.condition_service.repository.UserConditionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class UserConditionRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserConditionRepository userConditionRepository;

    @Autowired
    private KcalsInfoRepository kcalsInfoRepository;

    @Test
    public void createUserCondition_ShouldCreateUserConditionAndKcalsInfo() throws Exception {
        UserConditionDTO userConditionDTO = new UserConditionDTO();
        userConditionDTO.setGender(Gender.FEMALE);
        userConditionDTO.setAge((byte) 20);
        userConditionDTO.setHeight((short) 160);
        userConditionDTO.setWeight((short) 80);
        userConditionDTO.setGoal(Goal.LOSE);
        userConditionDTO.setIntensity(Intensity.MAX);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonConditionDTO = objectMapper.writeValueAsString(userConditionDTO);

        byte userConditionCount = (byte) userConditionRepository.findAll().size();
        byte kcalsInfoCount = (byte) kcalsInfoRepository.findAll().size();

        mockMvc.perform(post("/api/v1/condition")
                        .param("userId", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConditionDTO))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(userConditionRepository.findAll().size()).isEqualTo(userConditionCount + 1);
        assertThat(kcalsInfoRepository.findAll().size()).isEqualTo(kcalsInfoCount + 1);
    }

    @Test
    public void createUserCondition_ShouldReturnJsonWithException() throws Exception {
        UserConditionDTO userConditionDTO = new UserConditionDTO();
        userConditionDTO.setAge((byte) 20);
        userConditionDTO.setGender(Gender.MALE);
        userConditionDTO.setHeight((short) 190);
        userConditionDTO.setWeight((short) 80);
        userConditionDTO.setGoal(Goal.GAIN);
        userConditionDTO.setIntensity(Intensity.AVG);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonConditionDTO = objectMapper.writeValueAsString(userConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                        .param("userId", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request body is missing")));

        mockMvc.perform(post("/api/v1/condition")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConditionDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("UserConditionAlreadyExists | User condition with userId = 1 already exists")));

    }

    @Test
    public void getUserConditionByUserId_shouldReturnUserConditionById() throws Exception {
        mockMvc.perform(get("/api/v1/condition")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.gender").exists())
                .andExpect(jsonPath("$.age").exists())
                .andExpect(jsonPath("$.height").exists())
                .andExpect(jsonPath("$.weight").exists())
                .andExpect(jsonPath("$.intensity").exists())
                .andExpect(jsonPath("$.goal").exists());
    }

    @Test
    public void getUserConditionByUserId_shouldReturnJsonWithException() throws Exception {
        mockMvc.perform(get("/api/v1/condition")
                        .param("userId", "99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("UserConditionNotFound | User condition with userId = 99 not found")));
    }

    @Test
    public void updateCondition_shouldUpdateUserConditionAndKcalsInfo() throws Exception {
        UserCondition oldUserCondition = userConditionRepository.findByUserId(1);

        UserConditionDTO userConditionDTO = new UserConditionDTO();
        userConditionDTO.setGender(Gender.FEMALE);
        userConditionDTO.setAge((byte) 30);
        userConditionDTO.setHeight((short) 160);
        userConditionDTO.setWeight((short) 50);
        userConditionDTO.setGoal(Goal.GAIN);
        userConditionDTO.setIntensity(Intensity.MIN);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonConditionDTO = objectMapper.writeValueAsString(userConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                        .param("userId", "1")
                        .content(jsonConditionDTO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        UserCondition newUserCondition = userConditionRepository.findByUserId(1);

        assertThat(oldUserCondition.getUserId()).isEqualTo(newUserCondition.getUserId());
        assertThat(oldUserCondition.getAge()).isNotEqualTo(newUserCondition.getAge());
        assertThat(oldUserCondition.getHeight()).isNotEqualTo(newUserCondition.getHeight());
        assertThat(oldUserCondition.getGender()).isNotEqualByComparingTo(newUserCondition.getGender());
        assertThat(oldUserCondition.getAge()).isNotEqualTo(newUserCondition.getAge());
        assertThat(oldUserCondition.getHeight()).isNotEqualTo(newUserCondition.getHeight());
        assertThat(oldUserCondition.getWeight()).isNotEqualByComparingTo(newUserCondition.getWeight());
        assertThat(oldUserCondition.getIntensity()).isNotEqualByComparingTo(newUserCondition.getIntensity());
        assertThat(oldUserCondition.getGoal()).isNotEqualByComparingTo(newUserCondition.getGoal());
    }

    @Test
    public void updateCondition_shouldReturnJsonWithException() throws Exception {
        UserConditionDTO userConditionDTO = new UserConditionDTO();
        userConditionDTO.setGender(Gender.FEMALE);
        userConditionDTO.setAge((byte) 30);
        userConditionDTO.setHeight((short) 160);
        userConditionDTO.setWeight((short) 50);
        userConditionDTO.setGoal(Goal.GAIN);
        userConditionDTO.setIntensity(Intensity.MIN);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonConditionDTO = objectMapper.writeValueAsString(userConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request body is missing")));

        mockMvc.perform(put("/api/v1/condition")
                        .param("userId", "99")
                        .content(jsonConditionDTO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("UserConditionNotFound | User condition with userId = 99 not found")));
    }

    @Test
    public void getKcalsInfoByUserId_shouldReturnKcalsInfoByUserId() throws Exception {
        mockMvc.perform(get("/api/v1/kcals")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.protein").exists())
                .andExpect(jsonPath("$.fat").exists())
                .andExpect(jsonPath("$.carb").exists());
    }

    @Test
    public void getKCalsInfoByUserId_shouldRetrunKcalsInfoByUserId() throws Exception {
        mockMvc.perform(get("/api/v1/kcals")
                        .param("userId", "99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("UserConditionNotFound | User condition with userId = 99 not found")));
    }
}
