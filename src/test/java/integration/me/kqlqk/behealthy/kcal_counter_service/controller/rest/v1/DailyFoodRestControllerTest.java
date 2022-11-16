package integration.me.kqlqk.behealthy.kcal_counter_service.controller.rest.v1;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.condition_service.dto.DailyFoodDTO;
import me.kqlqk.behealthy.condition_service.service.DailyFoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class DailyFoodRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DailyFoodService dailyFoodService;

    @Test
    public void getDailyFoodForUser_shouldReturnListWithAllDailyFoodForUser() throws Exception {
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
                .andExpect(jsonPath("$[0].proteins").exists())
                .andExpect(jsonPath("$[0].fats").exists())
                .andExpect(jsonPath("$[0].carbs").exists());

        mockMvc.perform(get("/api/v1/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", "99"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void addDailyFoodForUser_shouldCreateAndAddToDbNewDailyFoodForUser() throws Exception {
        DailyFoodDTO dailyFoodDTO = new DailyFoodDTO();
        dailyFoodDTO.setName("Potatoes");
        dailyFoodDTO.setWeight(500);
        dailyFoodDTO.setKcals(200);
        dailyFoodDTO.setProteins(7);
        dailyFoodDTO.setFats(4);
        dailyFoodDTO.setCarbs(50);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonDailyFoodDTO = objectMapper.writeValueAsString(dailyFoodDTO);

        assertThat(dailyFoodService.getByUserId(1)).hasSize(2);

        mockMvc.perform(post("/api/v1/food")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDailyFoodDTO))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(dailyFoodService.getByUserId(1)).hasSize(3);
    }

    @Test
    public void deleteDailyFoodFromUser_shouldDeleteDailyFoodFromDb() throws Exception {
        assertThat(dailyFoodService.getByUserId(1)).hasSize(2);

        mockMvc.perform(delete("/api/v1/food/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productId", "1"))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(dailyFoodService.getByUserId(1)).hasSize(1);
    }
}