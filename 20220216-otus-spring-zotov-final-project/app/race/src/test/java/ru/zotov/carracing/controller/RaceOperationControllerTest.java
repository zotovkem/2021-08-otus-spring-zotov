package ru.zotov.carracing.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.concurrent.ListenableFuture;
import ru.zotov.carracing.abstractE2ETest.AbstractE2ETest;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.dto.RaceFinishDto;
import ru.zotov.carracing.event.FuelExpandEvent;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Created by ZotovES on 23.02.2022
 */
@DisplayName("E2E Тестирование контроллера заездов")
class RaceOperationControllerTest extends AbstractE2ETest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser("testUser")
    @DisplayName("Загрузить заезд")
    void raceLoadTest() throws Exception {
        when(kafkaTemplate.send(eq(Constants.KAFKA_RACE_START_TOPIC), any(FuelExpandEvent.class)))
                .thenReturn(mock(ListenableFuture.class));

        mockMvc.perform(post("/race/{id}/load", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-USER-ID", "62e9a377-6078-488f-bb4c-3de998293086"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("state", is("LOAD")))
                .andReturn();
    }

    @Test
    @WithMockUser("testUser")
    @DisplayName("Стартовать заезд")
    void raceStartTest() throws Exception {
        mockMvc.perform(post("/race/{id}/start", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-USER-ID", "62e9a377-6078-488f-bb4c-3de998293086"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("state", is("START")))
                .andReturn();
    }

    @Test
    @WithMockUser("testUser")
    @DisplayName("Финишировать заезд")
    void raceFinishTest() throws Exception {
        mockMvc.perform(post("/race/finish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new RaceFinishDto(3L, "e5d81ced-a80b-4ae1-ba1d-53ca7e7df0e6")))
                        .header("X-USER-ID", "62e9a377-6078-488f-bb4c-3de998293086"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(3)))
                .andExpect(jsonPath("state", is("FINISH_SUCCESS")))
                .andReturn();
    }

    @Test
    @WithMockUser("testUser")
    @DisplayName("Отменить заезд")
    void raceCancelTest() throws Exception {
        mockMvc.perform(post("/race/{id}/cancel", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-USER-ID", "62e9a377-6078-488f-bb4c-3de998293086"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(3)))
                .andExpect(jsonPath("state", is("CANCEL")))
                .andReturn();
    }

    @Test
    @WithMockUser("testUser")
    @DisplayName("Получить заезд по ид")
    void getRaceByIdTest() throws Exception {
        mockMvc.perform(get("/race")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("raceId","1")
                        .header("X-USER-ID", "62e9a377-6078-488f-bb4c-3de998293086"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("state", is("LOADED")))
                .andReturn();
    }
}
