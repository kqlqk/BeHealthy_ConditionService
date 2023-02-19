package integration.me.kqlqk.behealthy.condition_service.controller.rest.v1;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.condition_service.dto.user_kcal.AddUpdateUserKcalDTO;
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
public class UserKcalRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUserKcalByUserId_shouldReturnUserKcalByUserId() throws Exception {
        mockMvc.perform(get("/api/v1/kcal/user")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.kcal").exists())
                .andExpect(jsonPath("$.protein").exists())
                .andExpect(jsonPath("$.fat").exists())
                .andExpect(jsonPath("$.carb").exists())
                .andExpect(jsonPath("$.inPriority").exists())
                .andExpect(jsonPath("$.onlyKcal").exists());
    }

    @Test
    public void getUserKcalByUserId_shouldReturnJsonWithException() throws Exception {
        mockMvc.perform(get("/api/v1/kcal/user")
                                .param("userId", "2")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("UserKcal with userId = 2 not found")));
    }

    @Test
    public void createUserKcal_shouldCreateUserKcal() throws Exception {
        AddUpdateUserKcalDTO addUserKcalDTO = new AddUpdateUserKcalDTO(17, 1, 1, 1, false, false);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(addUserKcalDTO);

        mockMvc.perform(post("/api/v1/kcal/user")
                                .param("userId", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createUserKcal_shouldReturnJsonWithException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AddUpdateUserKcalDTO addUserKcalDTO = new AddUpdateUserKcalDTO(0, 1, 1, 1, false, false);
        String json = mapper.writeValueAsString(addUserKcalDTO);

        mockMvc.perform(post("/api/v1/kcal/user")
                                .param("userId", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Kcal should be between 1 and 9999")));


        addUserKcalDTO = new AddUpdateUserKcalDTO(10000, 1, 1, 1, false, false);
        json = mapper.writeValueAsString(addUserKcalDTO);

        mockMvc.perform(post("/api/v1/kcal/user")
                                .param("userId", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Kcal should be between 1 and 9999")));


        addUserKcalDTO = new AddUpdateUserKcalDTO(17, -1, 1, 1, false, false);
        json = mapper.writeValueAsString(addUserKcalDTO);

        mockMvc.perform(post("/api/v1/kcal/user")
                                .param("userId", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Protein should be between 0 and 999")));


        addUserKcalDTO = new AddUpdateUserKcalDTO(17, 1000, 1, 1, false, false);
        json = mapper.writeValueAsString(addUserKcalDTO);

        mockMvc.perform(post("/api/v1/kcal/user")
                                .param("userId", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Protein should be between 0 and 999")));


        addUserKcalDTO = new AddUpdateUserKcalDTO(17, 1, -1, 1, false, false);
        json = mapper.writeValueAsString(addUserKcalDTO);

        mockMvc.perform(post("/api/v1/kcal/user")
                                .param("userId", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat should be between 0 and 999")));


        addUserKcalDTO = new AddUpdateUserKcalDTO(17, 1, 1000, 1, false, false);
        json = mapper.writeValueAsString(addUserKcalDTO);

        mockMvc.perform(post("/api/v1/kcal/user")
                                .param("userId", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat should be between 0 and 999")));


        addUserKcalDTO = new AddUpdateUserKcalDTO(17, 1, 1, -1, false, false);
        json = mapper.writeValueAsString(addUserKcalDTO);

        mockMvc.perform(post("/api/v1/kcal/user")
                                .param("userId", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Carb should be between 0 and 999")));


        addUserKcalDTO = new AddUpdateUserKcalDTO(17, 1, 1, 1000, false, false);
        json = mapper.writeValueAsString(addUserKcalDTO);

        mockMvc.perform(post("/api/v1/kcal/user")
                                .param("userId", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Carb should be between 0 and 999")));
    }

    @Test
    public void updateUserKcal_shouldUpdateUserKcal() throws Exception {
        AddUpdateUserKcalDTO updateUserKcalDTO = new AddUpdateUserKcalDTO(17, 1, 1, 1, false, false);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(updateUserKcalDTO);

        mockMvc.perform(put("/api/v1/kcal/user")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserKcal_shouldReturnJsonWithException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AddUpdateUserKcalDTO updateUserKcalDTO = new AddUpdateUserKcalDTO(0, 1, 1, 1, false, false);
        String json = mapper.writeValueAsString(updateUserKcalDTO);

        mockMvc.perform(put("/api/v1/kcal/user")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Kcal should be between 1 and 9999")));


        updateUserKcalDTO = new AddUpdateUserKcalDTO(10000, 1, 1, 1, false, false);
        json = mapper.writeValueAsString(updateUserKcalDTO);

        mockMvc.perform(put("/api/v1/kcal/user")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Kcal should be between 1 and 9999")));


        updateUserKcalDTO = new AddUpdateUserKcalDTO(17, -1, 1, 1, false, false);
        json = mapper.writeValueAsString(updateUserKcalDTO);

        mockMvc.perform(put("/api/v1/kcal/user")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Protein should be between 0 and 999")));


        updateUserKcalDTO = new AddUpdateUserKcalDTO(17, 1000, 1, 1, false, false);
        json = mapper.writeValueAsString(updateUserKcalDTO);

        mockMvc.perform(put("/api/v1/kcal/user")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Protein should be between 0 and 999")));


        updateUserKcalDTO = new AddUpdateUserKcalDTO(17, 1, -1, 1, false, false);
        json = mapper.writeValueAsString(updateUserKcalDTO);

        mockMvc.perform(put("/api/v1/kcal/user")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat should be between 0 and 999")));


        updateUserKcalDTO = new AddUpdateUserKcalDTO(17, 1, 1000, 1, false, false);
        json = mapper.writeValueAsString(updateUserKcalDTO);

        mockMvc.perform(put("/api/v1/kcal/user")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat should be between 0 and 999")));


        updateUserKcalDTO = new AddUpdateUserKcalDTO(17, 1, 1, -1, false, false);
        json = mapper.writeValueAsString(updateUserKcalDTO);

        mockMvc.perform(put("/api/v1/kcal/user")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Carb should be between 0 and 999")));


        updateUserKcalDTO = new AddUpdateUserKcalDTO(17, 1, 1, 1000, false, false);
        json = mapper.writeValueAsString(updateUserKcalDTO);

        mockMvc.perform(put("/api/v1/kcal/user")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Carb should be between 0 and 999")));
    }
}
