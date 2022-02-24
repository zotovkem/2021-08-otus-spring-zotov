package ru.zotov.carracing.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.zotov.carracing.abstractE2ETest.AbstractE2ETest;
import ru.zotov.carracing.dto.RaceTemplateDto;
import ru.zotov.carracing.entity.RaceTemplate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Created by ZotovES on 23.02.2022
 */
@DisplayName("E2E Тестирование контроллера шаблонов заездов")
class RaceTemplateControllerTest extends AbstractE2ETest {
    @Autowired private MockMvc mockMvc;

    @Test
    @WithMockUser(value = "testPlayer")
    @DisplayName("Создать шаблон заезда")
    void createRaceTemplateTest() throws Exception {
        RaceTemplateDto raceTemplate = RaceTemplateDto.builder()
                .id(1L)
                .name("Тестовый заезд")
                .rewardId(1L)
                .trackId(1)
                .build();
        mockMvc.perform(post("/templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(raceTemplate))
                        .header("X-USER-ID","40d1e5f9-0dcb-44db-8de7-86104d8b3928"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("name", is("Тестовый заезд")))
                .andReturn();
    }

    @Test
    @WithMockUser(value = "testPlayer")
    @DisplayName("Получить шаблон заезда по ид")
    void getByIdTest() throws Exception  {
        mockMvc.perform(get("/templates/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-USER-ID","40d1e5f9-0dcb-44db-8de7-86104d8b3928"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("Тестовый заезд")))
                .andReturn();
    }

    @Test
    @WithMockUser(value = "testPlayer")
    @DisplayName("Получить список всех шаблонов")
    void getAllRaceTemplatesTest() throws Exception {
        mockMvc.perform(get("/templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-USER-ID","40d1e5f9-0dcb-44db-8de7-86104d8b3928"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn();
    }

    @Test
    @WithMockUser(value = "testPlayer")
    @DisplayName("Редактирование шаблона заезда")
    void updateRaceTemplateTest() throws Exception {
        RaceTemplateDto raceTemplate = RaceTemplateDto.builder()
                .id(1L)
                .name("Отредактированный заезд")
                .rewardId(1L)
                .trackId(1)
                .build();
        mockMvc.perform(put("/templates/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(raceTemplate))
                        .header("X-USER-ID","40d1e5f9-0dcb-44db-8de7-86104d8b3928"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("Отредактированный заезд")))
                .andReturn();
    }

    @Test
    @WithMockUser(value = "testPlayer")
    @DisplayName("Удалить шаблон по ид")
    void deleteByIdTest() throws Exception {
        mockMvc.perform(delete("/templates/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-USER-ID","40d1e5f9-0dcb-44db-8de7-86104d8b3928"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @DisplayName("Удалить шаблон по ид без заголовка с ид пользователя")
    void deleteByIdNoAuthorizeTest() throws Exception {
        mockMvc.perform(delete("/templates/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }
}
