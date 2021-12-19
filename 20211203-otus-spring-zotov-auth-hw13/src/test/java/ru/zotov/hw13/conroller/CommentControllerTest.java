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
import ru.zotov.hw13.dto.CommentDto;

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
@SpringBootTest(classes = Hw13Application.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN"})
@DisplayName("Тестирование контроллера комментариев")
class CommentControllerTest {
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
    @WithMockUser(username = "adult", roles = {"ADULT"})
    @DisplayName("Разрешено добавить комментарий взрослым")
    void createCommentForAdultTest() throws Exception {
        CommentDto commentDto = new CommentDto(null, 1L, "test", "автор", ZonedDateTime.now());
        String content = objectMapper.writeValueAsString(commentDto);
        mockMvc.perform(post("/api/comments").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("content", is("test")))
                .andExpect(jsonPath("author", is("автор")))
                .andExpect(jsonPath("createDate").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Запрещено добавить комментарий детям")
    @WithMockUser(username = "child", roles = {"CHILD"})
    void createCommentForbiddenForChildTest() throws Exception {
        CommentDto commentDto = new CommentDto(null, 1L, "test", "автор", ZonedDateTime.now());
        String content = objectMapper.writeValueAsString(commentDto);
        mockMvc.perform(post("/api/comments").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
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
    @DisplayName("Разрешено взрослым читать все комментарии ")
    @WithMockUser(username = "adult", roles = {"ADULT"})
    void commentsGetAllAdultTest() throws Exception {
        mockMvc.perform(get("/api/comments").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(1))))
                .andReturn();
    }

    @Test
    @DisplayName("Детям разрешено читать комментарии только к разрешенным книгам")
    @WithMockUser(username = "child", roles = {"CHILD"})
    void commentsGetAllFilteredChildTest() throws Exception {
        mockMvc.perform(get("/api/comments").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(lessThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].bookId", is(5)))
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
    @DisplayName("Разрешено взрослым получить комментарий по ид")
    @WithMockUser(username = "adult", roles = {"ADULT"})
    void getCommentByIdForAdultTest() throws Exception {
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
    @DisplayName("Запрещено детям получить комментарий по ид от взрослой книги")
    @WithMockUser(username = "child", roles = {"CHILD"})
    void getCommentByIdForbiddenForChildTest() throws Exception {
        mockMvc.perform(get("/api/comments/{id}", 2).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("Разрешено детям получить комментарий по ид для детской книги")
    @WithMockUser(username = "child", roles = {"CHILD"})
    void getCommentByIdForChildTest() throws Exception {
        mockMvc.perform(get("/api/comments/{id}", 10).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(10)))
                .andExpect(jsonPath("content", is("Как много комментарием нужно написать")))
                .andExpect(jsonPath("author", is("Тестов")))
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
    @WithMockUser(username = "adult", roles = {"ADULT"})
    @DisplayName("Взрослым запрещено редактировать комментарий чужой комментарий")
    void updateForeignCommentForbiddenForAdultTest() throws Exception {
        CommentDto commentDto = new CommentDto(2L, 1L, "текст комментария", "автор", ZonedDateTime.now());
        String content = objectMapper.writeValueAsString(commentDto);
        mockMvc.perform(put("/api/comments").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "adult", roles = {"ADULT"})
    @DisplayName("Разрешено взрослым редактировать свои комментарии")
    void updateOwnCommentForAdultTest() throws Exception {
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
    @WithMockUser(username = "child", roles = {"CHILD"})
    @DisplayName("Детям запрещено редактировать комментарии")
    void updateCommentForbiddenForChildTest() throws Exception {
        CommentDto commentDto = new CommentDto(2L, 1L, "текст комментария", "автор", ZonedDateTime.now());
        String content = objectMapper.writeValueAsString(commentDto);
        mockMvc.perform(put("/api/comments").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
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

    @Test
    @WithMockUser(username = "child", roles = {"CHILD"})
    @DisplayName("Запрещено удалять комментарии детям")
    void deleteByIdsForbiddenForChildTest() throws Exception {
        String content = objectMapper.writeValueAsString(List.of(5));
        mockMvc.perform(delete("/api/comments").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "adult", roles = {"ADULT"})
    @DisplayName("Запрещено удалять чужие комментарии взрослым")
    void deleteByForeignCommentsForbiddenForAdultTest() throws Exception {
        String content = objectMapper.writeValueAsString(List.of(4));
        mockMvc.perform(delete("/api/comments").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "adult", roles = {"ADULT"})
    @DisplayName("Разрешено взрослым удалять свои комментарии")
    void deleteByOwnCommentForAdultTest() throws Exception {
        mockMvc.perform(get("/api/comments/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andReturn();
        String content = objectMapper.writeValueAsString(List.of(1));
        mockMvc.perform(delete("/api/comments").content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        mockMvc.perform(get("/api/comments/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
