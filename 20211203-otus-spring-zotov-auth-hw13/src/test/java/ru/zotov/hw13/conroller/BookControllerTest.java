package ru.zotov.hw13.conroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.zotov.hw13.Hw13Application;
import ru.zotov.hw13.dto.AuthorDto;
import ru.zotov.hw13.dto.BookDto;
import ru.zotov.hw13.dto.GenreDto;

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
@SpringBootTest(classes = Hw13Application.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN"})
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
                .authors(Set.of(new AuthorDto(1L, null)))
                .genres(Set.of(new GenreDto(1L, null)))
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
    @DisplayName("Запрещено создание книги взрослым")
    @WithMockUser(username = "adult", roles = {"ADULT"})
    void createBookForbiddenAdultTest() throws Exception {
        BookDto bookDto = BookDto.builder()
                .authors(Set.of(new AuthorDto(1L, null)))
                .genres(Set.of(new GenreDto(1L, null)))
                .name("test")
                .releaseYear(2010)
                .build();
        String content = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(post("/api/books").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("Запрещено создание книги детям")
    @WithMockUser(username = "child", roles = {"CHILD"})
    void createBookForbiddenChildTest() throws Exception {
        BookDto bookDto = BookDto.builder()
                .authors(Set.of(new AuthorDto(1L, null)))
                .genres(Set.of(new GenreDto(1L, null)))
                .name("test")
                .releaseYear(2010)
                .build();
        String content = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(post("/api/books").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
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
    @WithMockUser(username = "adult", roles = {"ADULT"})
    @DisplayName("Получить все книги взрослым пользователем")
    void bookGetAdultUserAllTest() throws Exception {
        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "child", roles = {"CHILD"})
    @DisplayName("Получить книги только для детей")
    void bookGetChildUserAllTest() throws Exception {
        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].id", is(5)))
                .andReturn();
    }

    @Test
    @DisplayName("Получить книгу по ид")
    void getByIdTest() throws Exception {
        mockMvc.perform(get("/api/books/{id}", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(2)))
                .andExpect(jsonPath("name", is("Чистая архитектура")))
                .andExpect(jsonPath("releaseYear", is(2018)))
                .andExpect(jsonPath("authors").isNotEmpty())
                .andExpect(jsonPath("genres").isNotEmpty())
                .andExpect(jsonPath("comments").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Получить книгу по ид взрослым пользователем")
    @WithMockUser(username = "adult", roles = {"ADULT"})
    void getByIdForAdultTest() throws Exception {
        mockMvc.perform(get("/api/books/{id}", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(2)))
                .andExpect(jsonPath("name", is("Чистая архитектура")))
                .andExpect(jsonPath("releaseYear", is(2018)))
                .andExpect(jsonPath("authors").isNotEmpty())
                .andExpect(jsonPath("genres").isNotEmpty())
                .andExpect(jsonPath("comments").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Получить запрещенную книгу по ид ребенком")
    @WithMockUser(username = "child", roles = {"CHILD"})
    void getByIdForbiddenForChildTest() throws Exception {
        mockMvc.perform(get("/api/books/{id}", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("Редактировать книгу")
    void updateBookTest() throws Exception {
        BookDto bookDto = BookDto.builder()
                .id(1L)
                .authors(Set.of(new AuthorDto(1L, null)))
                .genres(Set.of(new GenreDto(1L, null)))
                .name("test")
                .releaseYear(2010)
                .build();
        String content = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(put("/api/books").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("test")))
                .andExpect(jsonPath("releaseYear", is(2010)))
                .andExpect(jsonPath("authors").isNotEmpty())
                .andExpect(jsonPath("genres").isNotEmpty())
                .andExpect(jsonPath("comments").isEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Запрещено редактировать книгу взрослым")
    @WithMockUser(username = "adult", roles = {"ADULT"})
    void updateBookForbiddenForAdultTest() throws Exception {
        BookDto bookDto = BookDto.builder()
                .id(1L)
                .authors(Set.of(new AuthorDto(1L, null)))
                .genres(Set.of(new GenreDto(1L, null)))
                .name("test")
                .releaseYear(2010)
                .build();
        String content = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(put("/api/books").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("Запрещено редактировать книгу детям")
    @WithMockUser(username = "child", roles = {"CHILD"})
    void updateBookForbiddenForChildTest() throws Exception {
        BookDto bookDto = BookDto.builder()
                .id(1L)
                .authors(Set.of(new AuthorDto(1L, null)))
                .genres(Set.of(new GenreDto(1L, null)))
                .name("test")
                .releaseYear(2010)
                .build();
        String content = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(put("/api/books").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("Удалить книги по списку ид")
    void deleteByIdTest() throws Exception {
        mockMvc.perform(get("/api/books/{id}", 6))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(6)))
                .andReturn();
        String content = objectMapper.writeValueAsString(List.of(4, 6));
        mockMvc.perform(delete("/api/books").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        mockMvc.perform(get("/api/books/{id}", 6))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("Запрещено удалять книги взрослым")
    @WithMockUser(username = "adult", roles = {"ADULT"})
    void deleteByIdForbiddenForAdultTest() throws Exception {
        String content = objectMapper.writeValueAsString(List.of(5, 6));
        mockMvc.perform(delete("/api/books").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("Запрещено удалять книги детям")
    @WithMockUser(username = "child", roles = {"CHILD"})
    void deleteByIdForbiddenForChildTest() throws Exception {
        String content = objectMapper.writeValueAsString(List.of(5, 6));
        mockMvc.perform(delete("/api/books").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

}
