package ru.zotov.hw6.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.zotov.hw6.dao.mapper.AuthorToMapEntryMapper;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Created by ZotovES on 11.10.2021
 */
@JdbcTest
@DisplayName("Тестирование репозитория ссылок книг на авторов")
@Import(AuthorRefBookDaoJdbcImpl.class)
class AuthorRefBookDaoJdbcImplTest {
    @Autowired private AuthorRefBookDaoJdbcImpl authorRefBookDao;

    @Test
    @DisplayName("Поиск ид авторов по списку ид книг")
    void findByBookIdsTest() {
        Map<Long, List<Long>> result = authorRefBookDao.findByBookIds(List.of(1L));

        assertThat(result).isNotEmpty().containsKey(1L).extractingByKey(1L).asList().hasSize(1).contains(1L);
    }

    @Test
    @DisplayName("Добавить ссылку на автора")
    void addRefAuthorTest() {
        authorRefBookDao.addRefAuthor(1L, 2L);

        Map<Long, List<Long>> result = authorRefBookDao.findByBookIds(List.of(1L));

        assertThat(result).isNotEmpty().containsKey(1L).extractingByKey(1L).asList().hasSize(2).containsAll(List.of(1L, 2L));
    }

    @Test
    @DisplayName("Удалить все ссылки на авторов")
    void deleteAllRefAuthorTest() {
        authorRefBookDao.deleteAllRefAuthor(1L);

        Map<Long, List<Long>> result = authorRefBookDao.findByBookIds(List.of(1L));

        assertThat(result).isEmpty();
    }

    @Configuration
    static class Config {
        @Bean
        AuthorToMapEntryMapper getMapper() {
            return new AuthorToMapEntryMapper();
        }
    }
}
