package ru.zotov.carracing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.zotov.carracing.baseE2ETest.AbstractE2ETest;
import ru.zotov.carracing.dto.RewardDto;
import ru.zotov.carracing.enums.RewardType;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Created by ZotovES on 22.02.2022
 */
@DisplayName("E2E Тестирование контроллера наград")
class RewardControllerTest extends AbstractE2ETest {
    @Autowired
    protected MockMvc mockMvc;

    @Test
    @DisplayName("Создание наград")
    void createRewardTest() throws Exception {
        RewardDto rewardDto = RewardDto.builder()
                .rewardType(RewardType.CAR)
                .name("Kia Rio")
                .total(1)
                .build();

        mockMvc.perform(post("/rewards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(rewardDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Kia Rio")))
                .andExpect(jsonPath("$.total", is(1)))
                .andExpect(jsonPath("$.rewardType", is("CAR")))
                .andReturn();
    }

    @Test
    @DisplayName("Получить награду по ид")
    void getByIdTest() throws Exception {
        mockMvc.perform(get("/rewards/{rewardId}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Награда за заезд")))
                .andExpect(jsonPath("$.total", is(100)))
                .andExpect(jsonPath("$.rewardType", is("MONEY")))
                .andReturn();
    }

    @Test
    @DisplayName("Получить список всех наград")
    void getAllRewardsTest() throws Exception {
        mockMvc.perform(get("/rewards")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].id",containsInAnyOrder(1)))
                .andReturn();
    }

    @Test
    @DisplayName("Редактировать награду")
    void updateRewardTest() throws Exception {
        RewardDto rewardDto = RewardDto.builder()
                .rewardType(RewardType.CAR)
                .name("Kia Rio")
                .total(1)
                .build();

        mockMvc.perform(put("/rewards/{rewardId}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(rewardDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Kia Rio")))
                .andExpect(jsonPath("$.total", is(1)))
                .andExpect(jsonPath("$.rewardType", is("CAR")))
                .andReturn();
    }

    @Test
    @DisplayName("Удалить награду по ид")
    void deleteByIdTest() throws Exception {
        mockMvc.perform(delete("/rewards/{rewardId}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
