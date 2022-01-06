package ru.zotov.hw16.conroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.zotov.hw16.Hw16Application;
import ru.zotov.hw16.dto.GenreDto;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Created by ZotovES on 22.11.2021
 */
@EnableConfigurationProperties
@SpringBootTest(classes = Hw16Application.class)
@AutoConfigureMockMvc
@DisplayName("Тестирование контроллера жанров")
class GenreControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Добавление жанра")
    void addGenreTest() throws Exception {
        String content = objectMapper.writeValueAsString(new GenreDto(null, "test"));
        mockMvc.perform(post("/api/genres").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("name", is("test")))
                .andReturn();
    }

    @Test
    @DisplayName("Получить жанр по ид")
    void getByIdTest() throws Exception {
        mockMvc.perform(get("/api/genres/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("1")))
                .andExpect(jsonPath("name", is("Детектив")))
                .andReturn();
    }

    @Test
    @DisplayName("Получить все жанры")
    void getAllTest() throws Exception {
        mockMvc.perform(get("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Редактировать жанр")
    void updateTest() throws Exception {
        String content = objectMapper.writeValueAsString(new GenreDto("1", "test"));
        mockMvc.perform(put("/api/genres").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("1")))
                .andExpect(jsonPath("name", is("test")))
                .andReturn();
    }

    @Test
    @DisplayName("Удалить жанры по списку ид")
    void deleteByListIdsTest() throws Exception {
        mockMvc.perform(get("/api/genres/{id}", 10))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("10")))
                .andReturn();
        String content = objectMapper.writeValueAsString(List.of("10"));
        mockMvc.perform(delete("/api/genres").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        mockMvc.perform(get("/api/genres/{id}", 10))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("Ошибка при удалении связанного с книгой жанра")
    void deleteByListIdsExceptionTest() throws Exception {
        String content = objectMapper.writeValueAsString(List.of("1"));
        mockMvc.perform(delete("/api/genres").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
