package ru.zotov.hw13.conroller;

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
import ru.zotov.hw13.Hw13Application;
import ru.zotov.hw13.dto.AuthorDto;

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
@SpringBootTest(classes = Hw13Application.class)
@AutoConfigureMockMvc
@DisplayName("Тестирование контроллера авторов")
class AuthorControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Создание автора")
    void createTest() throws Exception {
        String content = objectMapper.writeValueAsString(new AuthorDto(null, "test"));
        mockMvc.perform(post("/api/authors").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("fio", is("test")))
                .andReturn();
    }

    @Test
    @DisplayName("Получить всех авторов")
    void getAllTest() throws Exception {
        mockMvc.perform(get("/api/authors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Получить автора по ид")
    void getByIdTest() throws Exception {
        mockMvc.perform(get("/api/authors/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("fio", is("Роберт Мартин")))
                .andReturn();
    }

    @Test
    @DisplayName("Редактировать автора")
    void updateTest() throws Exception {
        String content = objectMapper.writeValueAsString(new AuthorDto(2L, "test"));
        mockMvc.perform(put("/api/authors").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("2")))
                .andExpect(jsonPath("fio", is("test")))
                .andReturn();
    }

    @Test
    @DisplayName("Удалить авторов по списку ид")
    void deleteByListIdsTest() throws Exception {
        mockMvc.perform(get("/api/authors/{id}", 10))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("10")))
                .andReturn();
        String content = objectMapper.writeValueAsString(List.of("10"));
        mockMvc.perform(delete("/api/authors").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        mockMvc.perform(get("/api/authors/{id}", 10))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
