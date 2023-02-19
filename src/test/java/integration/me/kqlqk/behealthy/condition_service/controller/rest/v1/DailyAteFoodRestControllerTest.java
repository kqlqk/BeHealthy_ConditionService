package integration.me.kqlqk.behealthy.condition_service.controller.rest.v1;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.condition_service.dto.daily_ate_food.AddDailyAteFoodDTO;
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
public class DailyAteFoodRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getDailyAteFoodForUser_shouldReturnListWithAllDailyAteFoodForUser() throws Exception {
        mockMvc.perform(get("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].weight").exists())
                .andExpect(jsonPath("$[0].kcal").exists())
                .andExpect(jsonPath("$[0].protein").exists())
                .andExpect(jsonPath("$[0].fat").exists())
                .andExpect(jsonPath("$[0].carb").exists());
    }

    @Test
    public void getDailyAteFoodForUser_shouldThrowException() throws Exception {
        mockMvc.perform(get("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("DailyAteFood with userId = 0 not found")));
    }

    @Test
    public void addDailyAteFood_shouldAddDailyAteFoodToDb() throws Exception {
        AddDailyAteFoodDTO addDailyAteFoodDTO = new AddDailyAteFoodDTO();
        addDailyAteFoodDTO.setName("name");
        addDailyAteFoodDTO.setWeight(333.2);
        addDailyAteFoodDTO.setProtein(10);
        addDailyAteFoodDTO.setFat(20);
        addDailyAteFoodDTO.setCarb(30);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(addDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addDailyAteFood_shouldThrowException() throws Exception {
        AddDailyAteFoodDTO addDailyAteFoodDTO = new AddDailyAteFoodDTO("a", 333.2, 10, 20, 30);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(addDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Product name should be between 2 and 50 characters")));


        addDailyAteFoodDTO = new AddDailyAteFoodDTO(null, 333.2, 10, 20, 30);
        json = mapper.writeValueAsString(addDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Name cannot be null")));


        addDailyAteFoodDTO = new AddDailyAteFoodDTO("name", 0, 10, 20, 30);
        json = mapper.writeValueAsString(addDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Weight should be > 0")));


        addDailyAteFoodDTO = new AddDailyAteFoodDTO("name", 99999, 10, 20, 30);
        json = mapper.writeValueAsString(addDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Weight should be < 10000")));


        addDailyAteFoodDTO = new AddDailyAteFoodDTO("name", 333.2, -1, 20, 30);
        json = mapper.writeValueAsString(addDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Protein should be > -1")));


        addDailyAteFoodDTO = new AddDailyAteFoodDTO("name", 333.2, 1000, 20, 30);
        json = mapper.writeValueAsString(addDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Protein should be < 1000")));


        addDailyAteFoodDTO = new AddDailyAteFoodDTO("name", 333.2, 10, -1, 30);
        json = mapper.writeValueAsString(addDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat should be > -1")));


        addDailyAteFoodDTO = new AddDailyAteFoodDTO("name", 333.2, 10, 1000, 30);
        json = mapper.writeValueAsString(addDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat should be < 1000")));


        addDailyAteFoodDTO = new AddDailyAteFoodDTO("name", 333.2, 10, 20, -1);
        json = mapper.writeValueAsString(addDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Carb should be > -1")));


        addDailyAteFoodDTO = new AddDailyAteFoodDTO("name", 333.2, 10, 20, 1000);
        json = mapper.writeValueAsString(addDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Carb should be < 1000")));
    }

    @Test
    public void deleteDailyAteFoodFromUser_shouldDeleteDailyAteFoodFromDb() throws Exception {
        mockMvc.perform(delete("/api/v1/food/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("productId", "1")
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}