package integration.me.kqlqk.behealthy.condition_service.controller.rest.v1;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.condition_service.dto.user_condition.AddUpdateUserConditionDTO;
import me.kqlqk.behealthy.condition_service.model.enums.Activity;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class UserConditionRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUserConditionByUserId_shouldReturnUserCondition() throws Exception {
        mockMvc.perform(get("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.dailyKcalDTO").exists())
                .andExpect(jsonPath("$.dailyKcalDTO.protein").exists())
                .andExpect(jsonPath("$.dailyKcalDTO.fat").exists())
                .andExpect(jsonPath("$.dailyKcalDTO.carb").exists())
                .andExpect(jsonPath("$.gender").exists())
                .andExpect(jsonPath("$.age").exists())
                .andExpect(jsonPath("$.height").exists())
                .andExpect(jsonPath("$.weight").exists())
                .andExpect(jsonPath("$.activity").exists())
                .andExpect(jsonPath("$.goal").exists())
                .andExpect(jsonPath("$.fatPercent").exists());
    }

    @Test
    public void getUserConditionByUserId_shouldReturnJsonWithException() throws Exception {
        mockMvc.perform(get("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("User condition with userId = 0 not found")));

        mockMvc.perform(get("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));
    }

    @Test
    public void createUserConditionByUserId_shouldCreateUserCondition() throws Exception {
        AddUpdateUserConditionDTO addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 15);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createUserConditionByUserId_shouldReturnJsonWithException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AddUpdateUserConditionDTO addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(null, 30, 170, 80, Activity.MIN, Goal.LOSE, 15);
        String json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Gender cannot be null")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 10, 170, 80, Activity.MIN, Goal.LOSE, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Age should be between 15 and 60")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 70, 170, 80, Activity.MIN, Goal.LOSE, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Age should be between 15 and 60")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 140, 80, Activity.MIN, Goal.LOSE, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Height should be between 150 and 200")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 30, Activity.MIN, Goal.LOSE, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Weight should be between 40 and 140")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 150, Activity.MIN, Goal.LOSE, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Weight should be between 40 and 140")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, null, Goal.LOSE, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Activity cannot be null")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, null, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Goal cannot be null")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 2);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("FatPercent should be between 3 and 40")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 50);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("FatPercent should be between 3 and 40")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 1, 10, 10, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between chest and ilium should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 60, 10, 10, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between chest and ilium should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 10, 3, 10, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between navel and lower belly should be between 5 and 70")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 10, 71, 10, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between navel and lower belly should be between 5 and 70")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 10, 10, 1, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between nipple and armpit should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 10, 10, 51, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between nipple and armpit should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 10, 10, 10, 1);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between nipple and upper chest should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 10, 10, 10, 51);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between nipple and upper chest should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.FEMALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 1, 10, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between shoulder and elbow should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.FEMALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 51, 10, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "3")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between shoulder and elbow should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.FEMALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 20);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);
        mockMvc.perform(post("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));
    }

    @Test
    public void updateUserConditionByUserId_shouldUpdateUserCondition() throws Exception {
        AddUpdateUserConditionDTO addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 15);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserConditionByUserId_shouldReturnJsonWithException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AddUpdateUserConditionDTO addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(null, 30, 170, 80, Activity.MIN, Goal.LOSE, 15);
        String json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Gender cannot be null")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 10, 170, 80, Activity.MIN, Goal.LOSE, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Age should be between 15 and 60")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 70, 170, 80, Activity.MIN, Goal.LOSE, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Age should be between 15 and 60")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 140, 80, Activity.MIN, Goal.LOSE, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Height should be between 150 and 200")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 30, Activity.MIN, Goal.LOSE, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Weight should be between 40 and 140")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 150, Activity.MIN, Goal.LOSE, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Weight should be between 40 and 140")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, null, Goal.LOSE, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Activity cannot be null")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, null, 15);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Goal cannot be null")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 2);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("FatPercent should be between 3 and 40")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 50);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("FatPercent should be between 3 and 40")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 1, 10, 10, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between chest and ilium should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 60, 10, 10, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between chest and ilium should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 10, 3, 10, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between navel and lower belly should be between 5 and 70")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 10, 71, 10, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between navel and lower belly should be between 5 and 70")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 10, 10, 1, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between nipple and armpit should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 10, 10, 51, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between nipple and armpit should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 10, 10, 10, 1);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between nipple and upper chest should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.MALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 10, 10, 10, 51);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between nipple and upper chest should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.FEMALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 1, 10, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between shoulder and elbow should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.FEMALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 51, 10, 10);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);

        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat fold between shoulder and elbow should be between 2 and 50")));


        addUpdateUserConditionDTO =
                new AddUpdateUserConditionDTO(Gender.FEMALE, 30, 170, 80, Activity.MIN, Goal.LOSE, 20);
        json = mapper.writeValueAsString(addUpdateUserConditionDTO);
        mockMvc.perform(put("/api/v1/condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));
    }
}
