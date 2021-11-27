package ru.zotov.hw11.conroller;

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
import ru.zotov.hw11.Hw11Application;
import ru.zotov.hw11.dto.AuthorDto;
import ru.zotov.hw11.dto.BookDto;
import ru.zotov.hw11.dto.GenreDto;

import java.util.List;
import java.util.Set;

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
@SpringBootTest(classes = Hw11Application.class)
@AutoConfigureMockMvc
@DisplayName("Тестирование контроллера книг")
class BookControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Создание книги")
    void createBookTest() throws Exception {
        BookDto bookDto = BookDto.builder()
                .authors(Set.of(new AuthorDto("1", null)))
                .genres(Set.of(new GenreDto("1", null)))
                .name("test")
                .releaseYear(2010)
                .build();
        String content = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(post("/api/books").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("name", is("test")))
                .andExpect(jsonPath("releaseYear", is(2010)))
                .andExpect(jsonPath("authors").isNotEmpty())
                .andExpect(jsonPath("genres").isNotEmpty())
                .andExpect(jsonPath("comments").isEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Получить все книги")
    void bookGetAllTest() throws Exception {
        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Получить книгу по ид")
    void getByIdTest() throws Exception {
        mockMvc.perform(get("/api/books/{id}", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("2")))
                .andExpect(jsonPath("name", is("Чистая архитектура")))
                .andExpect(jsonPath("releaseYear", is(2018)))
                .andExpect(jsonPath("authors").isNotEmpty())
                .andExpect(jsonPath("genres").isNotEmpty())
                .andExpect(jsonPath("comments").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Редактировать книгу")
    void updateBookTest() throws Exception {
        BookDto bookDto = BookDto.builder()
                .id("1")
                .authors(Set.of(new AuthorDto("1", null)))
                .genres(Set.of(new GenreDto("1", null)))
                .name("test")
                .releaseYear(2010)
                .build();
        String content = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(post("/api/books").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is("1")))
                .andExpect(jsonPath("name", is("test")))
                .andExpect(jsonPath("releaseYear", is(2010)))
                .andExpect(jsonPath("authors").isNotEmpty())
                .andExpect(jsonPath("genres").isNotEmpty())
                .andExpect(jsonPath("comments").isEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Удалить книги по списку ид")
    void deleteByIdTest() throws Exception {
        mockMvc.perform(get("/api/books/{id}", 5))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("5")))
                .andReturn();
        String content = objectMapper.writeValueAsString(List.of("5", "6"));
        mockMvc.perform(delete("/api/books").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        mockMvc.perform(get("/api/books/{id}", 5))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

}
