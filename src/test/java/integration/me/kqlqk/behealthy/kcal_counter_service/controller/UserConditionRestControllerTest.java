package integration.me.kqlqk.behealthy.kcal_counter_service.controller;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.kcal_counter_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.kcal_counter_service.dto.KcalsInfoDTO;
import me.kqlqk.behealthy.kcal_counter_service.model.Intensity;
import me.kqlqk.behealthy.kcal_counter_service.repository.KcalsInfoRepository;
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

    @Test
    public void createUserCondition_ShouldCreateUserCondition() throws Exception {
        KcalsInfoDTO kcalsInfoDTO = new KcalsInfoDTO();
        kcalsInfoDTO.setProtein((short) 100);
        kcalsInfoDTO.setFat((short) 60);
        kcalsInfoDTO.setCarb((short) 150);

        UserConditionDTO userConditionDTO = new UserConditionDTO();
        userConditionDTO.setKcalsInfoDTO(kcalsInfoDTO);
        userConditionDTO.setWeight((short) 80);
        userConditionDTO.setIntensity(Intensity.AVG);
        userConditionDTO.setUserId(4);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonConditionDTO = objectMapper.writeValueAsString(userConditionDTO);

        mockMvc.perform(post("/api/v1/create_condition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonConditionDTO))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(userConditionService.findById(3)).isNotNull();
        assertThat(userConditionService.findByUserId(4)).isNotNull();
    }

    @Test
    public void createUserCondition_ShouldReturnJsonWithException() throws Exception {
        KcalsInfoDTO kcalsInfoDTO = new KcalsInfoDTO();
        kcalsInfoDTO.setProtein((short) 100);
        kcalsInfoDTO.setFat((short) 60);
        kcalsInfoDTO.setCarb((short) 150);

        UserConditionDTO userConditionDTO = new UserConditionDTO();
        userConditionDTO.setKcalsInfoDTO(kcalsInfoDTO);
        userConditionDTO.setWeight((short) 80);
        userConditionDTO.setIntensity(Intensity.AVG);
        userConditionDTO.setUserId(1);

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
