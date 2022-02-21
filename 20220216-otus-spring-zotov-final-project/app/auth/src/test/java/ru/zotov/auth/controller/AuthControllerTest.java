package ru.zotov.auth.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.zotov.auth.baseE2ETest.AbstractE2ETest;
import ru.zotov.carracing.dto.LoginDto;
import ru.zotov.carracing.dto.RegisterUserDto;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Created by ZotovES on 19.02.2022
 */
@DisplayName("E2E Тест контроллера аутентификации")
class AuthControllerTest extends AbstractE2ETest {
    @Autowired
    protected MockMvc mockMvc;

    @Test
    @DisplayName("Регистрация пользователя")
    void registerUserTest() throws Exception {
        UUID profileId = UUID.randomUUID();
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new RegisterUserDto(null, "test", profileId, profileId + "@mail.ru"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname", is("test")))
                .andExpect(jsonPath("$.email", is(profileId + "@mail.ru")))
                .andReturn();
    }

    @Test
    @DisplayName("Логин пользователя")
    void loginTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new LoginDto("test@mail.ru", "password"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.refreshToken", notNullValue()))
                .andExpect(jsonPath("$.token", notNullValue()))
                .andReturn();
    }

    @Test
    @DisplayName("Логин пользователя с не верными паролем")
    void loginFailedTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new LoginDto("test@mail.ru", "test"))))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @DisplayName("Рефреш токена")
    void refreshTest() throws Exception {
        var refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QG1haWwucnUiLCJpYXQiOjE2NDU0MjQ2NjAsImV4cCI6MTA0MTY0NDUyMDB9" +
                ".hOTGPvy6278DbRK03gazf_nbLuTF5DUav6FYCHIHVkg";
        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(refreshToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.refreshToken", notNullValue()))
                .andExpect(jsonPath("$.token", notNullValue()))
                .andReturn();
    }

    @Test
    @DisplayName("Аутентификация")
    void authTest() throws Exception {
        var token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0Iiwicm9sZXMiOltdLCJlbWFpbCI6InRlc3RAbWFpbC5ydSIsInVzZ" +
                "XJJZCI6ImFjZTk0MmExLTIyMGMtNGIyNS05ZmRhLWJmNTgwZjJiZmM4ZiIsImlhdCI6MTY0NTQzODE0MCwiZXhwIjoxMDQx" +
                "NjQ0NTIwMH0.ZCiAeey7TGpua1qvi7m-7vRvFiWe6QRB3bDBblO-rtU";
        mockMvc.perform(get("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-TOKEN", token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("X-User-Id", "ace942a1-220c-4b25-9fda-bf580f2bfc8f"))
                .andExpect(header().string("X-Email", "test@mail.ru"))
                .andExpect(header().string("X-Nickname", "test"))
                .andReturn();
    }

    @Test
    @DisplayName("Аутентификация с неверным токеном ")
    void authFailTest() throws Exception {
        String token = "";
        mockMvc.perform(get("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-TOKEN", token)
                )
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(header().string("X-User-Id", nullValue()))
                .andExpect(header().string("X-Email", nullValue()))
                .andExpect(header().string("X-Nickname", nullValue()))
                .andReturn();
    }

    @Test
    @DisplayName("Восстановление пароля")
    void recoveryTest() throws Exception {
        mockMvc.perform(post("/auth/recovery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("test@mail.ru"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
