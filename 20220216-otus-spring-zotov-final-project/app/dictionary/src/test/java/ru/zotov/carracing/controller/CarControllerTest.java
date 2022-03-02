package ru.zotov.carracing.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.zotov.carracing.baseE2ETest.AbstractE2ETest;
import ru.zotov.carracing.dto.CarDto;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Created by ZotovES on 22.02.2022
 */
@DisplayName("E2E Тестирование контроллера машин")
class CarControllerTest extends AbstractE2ETest {
    @Autowired
    protected MockMvc mockMvc;

    @Test
    @DisplayName("Создание авто")
    void createCarTest() throws Exception {
        UUID carId = UUID.randomUUID();
        CarDto carDto = CarDto.builder()
                .name("Kia Rio")
                .carId(carId)
                .power(102)
                .maxSpeed(250)
                .build();

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Kia Rio")))
                .andExpect(jsonPath("$.carId", is(carId.toString())))
                .andExpect(jsonPath("$.power", is(102)))
                .andExpect(jsonPath("$.maxSpeed", is(250)))
                .andReturn();
    }

    @Test
    @DisplayName("Получить авто по внешнему ид")
    void getByCarIdTest() throws Exception{
        String carId = "c6c496b9-1fb7-400e-a662-f01b7f25df82";
        mockMvc.perform(get("/cars/{carId}", carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Audi TT")))
                .andExpect(jsonPath("$.carId", is(carId)))
                .andExpect(jsonPath("$.power", is(284)))
                .andExpect(jsonPath("$.maxSpeed", is(236)))
                .andReturn();
    }

    @Test
    @DisplayName("Редактировать авто")
    void updateByCarIdTest() throws Exception{
        String carId = "d5422001-a1a1-4487-97c3-55e627ab23a5";
        CarDto carDto = CarDto.builder()
                .name("Kia Rio")
                .carId(UUID.fromString(carId))
                .power(102)
                .maxSpeed(250)
                .build();

        mockMvc.perform(put("/cars/{carId}", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Kia Rio")))
                .andExpect(jsonPath("$.carId", is(carId.toString())))
                .andExpect(jsonPath("$.power", is(102)))
                .andExpect(jsonPath("$.maxSpeed", is(250)))
                .andReturn();
    }

    @Test
    @DisplayName("Удалить авто по ид")
    void deleteByIdCarTest() throws Exception{
        String carId = "c6c496b9-1fb7-400e-a662-f01b7f25df82";
        mockMvc.perform(delete("/cars/{carId}", carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @DisplayName("Получить все авто")
    void getAllCarsTest() throws Exception{
        mockMvc.perform(get("/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].carId",
                        containsInAnyOrder("c6c496b9-1fb7-400e-a662-f01b7f25df82", "d5422001-a1a1-4487-97c3-55e627ab23a5")))
                .andReturn();
    }
}
