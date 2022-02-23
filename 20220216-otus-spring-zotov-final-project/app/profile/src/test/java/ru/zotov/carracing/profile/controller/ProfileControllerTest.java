package ru.zotov.carracing.profile.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.zotov.carracing.dto.RegisterUserDto;
import ru.zotov.carracing.profile.AbstractE2ETest.AbstractE2ETest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Created by ZotovES on 23.02.2022
 */
@DisplayName("E2E Тестирование контроллера профиля")
class ProfileControllerTest extends AbstractE2ETest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получить текущий профиль игрока ")
    void getCurrentProfileTest() throws Exception {
        mockMvc.perform(get("/profiles/current")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-USER-ID","40d1e5f9-0dcb-44db-8de7-86104d8b3928"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("externalId", is("40d1e5f9-0dcb-44db-8de7-86104d8b3928")))
                .andReturn();
    }
}
