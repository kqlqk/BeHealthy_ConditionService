package integration.me.kqlqk.behealthy.condition_service.controller.rest.v1;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.condition_service.dto.daily_ate_food.AddUpdateDailyAteFoodDTO;
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
    public void getDailyAllAteFoodForUser_shouldReturnListWithAllDailyAteFoodForUser() throws Exception {
        mockMvc.perform(get("/api/v1/food/all")
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
                .andExpect(jsonPath("$[0].carb").exists())
                .andExpect(jsonPath("$[0].today").exists());
    }

    @Test
    public void getDailyAllAteFoodForUser_shouldReturnJsonWithException() throws Exception {
        mockMvc.perform(get("/api/v1/food/all")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));

        mockMvc.perform(get("/api/v1/food/all")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("DailyAteFood with userId = 0 not found")));
    }

    @Test
    public void getDailyAteFoodForUser_shouldReturnDailyAteFoodForUser() throws Exception {
        mockMvc.perform(get("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .param("productName", "rice"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.weight").exists())
                .andExpect(jsonPath("$.kcal").exists())
                .andExpect(jsonPath("$.protein").exists())
                .andExpect(jsonPath("$.fat").exists())
                .andExpect(jsonPath("$.carb").exists())
                .andExpect(jsonPath("$.today").exists());
    }

    @Test
    public void getDailyAteFoodForUser_shouldReturnJsonWithException() throws Exception {
        mockMvc.perform(get("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("productName", "rice"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));

        mockMvc.perform(get("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'productName' for method parameter type String is not present")));

        mockMvc.perform(get("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("productName", "rice")
                                .param("userId", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("DailyAteFood with name = rice and userId = 0 not found")));
    }

    @Test
    public void saveDailyAteFood_shouldSaveDailyAteFoodToDb() throws Exception {
        AddUpdateDailyAteFoodDTO addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO();
        addUpdateDailyAteFoodDTO.setName("name");
        addUpdateDailyAteFoodDTO.setWeight(333.2);
        addUpdateDailyAteFoodDTO.setProtein(10);
        addUpdateDailyAteFoodDTO.setFat(20);
        addUpdateDailyAteFoodDTO.setCarb(30);
        addUpdateDailyAteFoodDTO.setToday(true);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void saveDailyAteFood_shouldReturnJsonException() throws Exception {
        AddUpdateDailyAteFoodDTO addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("a", 333.2, 10, 20, 30, true);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Name should be between 2 and 50 characters")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO(null, 333.2, 10, 20, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Name cannot be null")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 0, 10, 20, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Weight should be > 0")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 99999, 10, 20, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Weight should be < 10000")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, -1, 20, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Protein should be > -1")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, 100, 20, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Protein should be < 100")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, 10, -1, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat should be > -1")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, 10, 100, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat should be < 100")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, 10, 20, -1, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Carb should be > -1")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, 10, 20, 100, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Carb should be < 100")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, 10, 20, 10, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);
        mockMvc.perform(post("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));
    }

    @Test
    public void updateDailyAteFood_shouldUpdateDailyAteFoodToDb() throws Exception {
        AddUpdateDailyAteFoodDTO addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO();
        addUpdateDailyAteFoodDTO.setName("rice");
        addUpdateDailyAteFoodDTO.setWeight(333.2);
        addUpdateDailyAteFoodDTO.setProtein(10);
        addUpdateDailyAteFoodDTO.setFat(20);
        addUpdateDailyAteFoodDTO.setCarb(30);
        addUpdateDailyAteFoodDTO.setToday(true);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(put("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateDailyAteFood_shouldReturnJsonException() throws Exception {
        AddUpdateDailyAteFoodDTO addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("a", 333.2, 10, 20, 30, true);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(put("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .param("productId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Name should be between 2 and 50 characters")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO(null, 333.2, 10, 20, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(put("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .param("productId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Name cannot be null")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 0, 10, 20, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(put("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .param("productId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Weight should be > 0")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 99999, 10, 20, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(put("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .param("productId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Weight should be < 10000")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, -1, 20, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(put("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .param("productId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Protein should be > -1")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, 100, 20, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(put("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .param("productId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Protein should be < 100")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, 10, -1, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(put("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .param("productId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat should be > -1")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, 10, 100, 30, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(put("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .param("productId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Fat should be < 100")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, 10, 20, -1, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(put("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .param("productId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Carb should be > -1")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, 10, 20, 100, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);

        mockMvc.perform(put("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1")
                                .param("productId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Carb should be < 100")));


        addUpdateDailyAteFoodDTO = new AddUpdateDailyAteFoodDTO("name", 333.2, 10, 20, 10, true);
        json = mapper.writeValueAsString(addUpdateDailyAteFoodDTO);
        mockMvc.perform(put("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("productId", "1")
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));
    }


    @Test
    public void deleteDailyAteFoodFromUser_shouldDeleteDailyAteFoodFromDb() throws Exception {
        mockMvc.perform(delete("/api/v1/food/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("productName", "rice")
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteDailyAteFoodFromUser_shouldReturnJsonException() throws Exception {
        mockMvc.perform(delete("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("productName", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));

        mockMvc.perform(delete("/api/v1/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'productName' for method parameter type String is not present")));
    }
}