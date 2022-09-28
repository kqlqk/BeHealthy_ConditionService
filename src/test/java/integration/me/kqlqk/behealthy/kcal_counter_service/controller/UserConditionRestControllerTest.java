package integration.me.kqlqk.behealthy.kcal_counter_service.controller;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.kcal_counter_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Gender;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Goal;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Intensity;
import me.kqlqk.behealthy.kcal_counter_service.repository.KcalsInfoRepository;
import me.kqlqk.behealthy.kcal_counter_service.repository.UserConditionRepository;
import me.kqlqk.behealthy.kcal_counter_service.service.UserConditionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class UserConditionRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserConditionService userConditionService;

    @Autowired
    private UserConditionRepository userConditionRepository;

    @Autowired
    private KcalsInfoRepository kcalsInfoRepository;

    @Test
    public void createUserCondition_ShouldCreateUserCondition() throws Exception {
        UserConditionDTO userConditionDTO = new UserConditionDTO();
        userConditionDTO.setUserId(4);
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

        mockMvc.perform(post("/api/v1/create_condition")
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
        userConditionDTO.setUserId(1);
        userConditionDTO.setAge((byte) 20);
        userConditionDTO.setGender(Gender.MALE);
        userConditionDTO.setHeight((short) 190);
        userConditionDTO.setWeight((short) 80);
        userConditionDTO.setGoal(Goal.GAIN);
        userConditionDTO.setIntensity(Intensity.AVG);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonConditionDTO = objectMapper.writeValueAsString(userConditionDTO);

        mockMvc.perform(post("/api/v1/create_condition")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request body is missing")));

        mockMvc.perform(post("/api/v1/create_condition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConditionDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("User condition with userId = 1 already exists")));

    }
}
