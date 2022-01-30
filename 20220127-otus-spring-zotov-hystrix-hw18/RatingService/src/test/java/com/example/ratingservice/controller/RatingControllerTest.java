package com.example.ratingservice.controller;

import com.example.ratingservice.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Created by ZotovES on 30.01.2022
 */
@DisplayName("Тестирование контролера рейтинга книг")
class RatingControllerTest extends AbstractTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Тестирование получения рейтинга книг")
    void getBookRatingTest() throws Exception {
        when(failureService.getRandomSleep()).thenReturn(0);

        mockMvc.perform(post("/ratings/book/{id}", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("bookId", is(2)))
                .andExpect(jsonPath("rating").isNotEmpty())
                .andReturn();
    }
}
