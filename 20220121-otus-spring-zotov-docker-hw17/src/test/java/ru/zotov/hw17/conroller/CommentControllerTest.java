package ru.zotov.hw17.conroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.zotov.hw17.AbstractTest;
import ru.zotov.hw17.dto.CommentDto;

import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Created by ZotovES on 19.12.2021
 */
@DisplayName("Тестирование контроллера комментариев")
class CommentControllerTest extends AbstractTest {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Добавить комментарий")
    void createCommentTest() throws Exception {
        CommentDto commentDto = new CommentDto(null, 1L, "текст комментария", "автор", ZonedDateTime.now());
        String content = objectMapper.writeValueAsString(commentDto);
        mockMvc.perform(post("/api/comments").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("content", is("текст комментария")))
                .andExpect(jsonPath("author", is("автор")))
                .andExpect(jsonPath("createDate").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Получить все комментарии")
    void commentsGetAllTest() throws Exception {
        mockMvc.perform(get("/api/comments").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Получить комментарий по ид")
    void getCommentByIdTest() throws Exception {
        mockMvc.perform(get("/api/comments/{id}", 2).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(2)))
                .andExpect(jsonPath("content", is("Хорошая книга")))
                .andExpect(jsonPath("author", is("ЗотовЕС")))
                .andExpect(jsonPath("createDate").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Редактировать комментарий")
    void updateCommentTest() throws Exception {
        CommentDto commentDto = new CommentDto(1L, 1L, "текст комментария", "автор", ZonedDateTime.now());
        String content = objectMapper.writeValueAsString(commentDto);
        mockMvc.perform(put("/api/comments").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("content", is("текст комментария")))
                .andExpect(jsonPath("author", is("автор")))
                .andExpect(jsonPath("createDate").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Удалить комментарии")
    void deleteByIdsTest() throws Exception {
        mockMvc.perform(get("/api/comments/{id}", 3))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(3)))
                .andReturn();
        String content = objectMapper.writeValueAsString(List.of(3));
        mockMvc.perform(delete("/api/comments").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        mockMvc.perform(get("/api/comments/{id}", 3))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
