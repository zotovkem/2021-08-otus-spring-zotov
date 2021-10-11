package ru.zotov.hw5.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Created by ZotovES on 11.10.2021
 */
@JdbcTest
@DisplayName("Тестирование репозитория ссылок")
@Import(GenreRefBookDaoJdbcImpl.class)
class GenreRefBookDaoJdbcImplTest {
    @Autowired private GenreRefBookDaoJdbcImpl genreRefBookDao;

    @Test
    @DisplayName("Найти жанры по списку ид книг")
    void findByBookIdsTest() {
        Map<Long, List<Long>> result = genreRefBookDao.findByBookIds(List.of(1L));

        assertThat(result).isNotEmpty().containsKey(1L).extractingByKey(1L).asList().hasSize(1).contains(1L);
    }

    @Test
    @DisplayName("Добавить связь жанра и книги")
    void addGenreTest() {
        genreRefBookDao.addRefGenre(1L, 2L);

        Map<Long, List<Long>> result = genreRefBookDao.findByBookIds(List.of(1L));

        assertThat(result).isNotEmpty().containsKey(1L).extractingByKey(1L).asList().hasSize(2).containsAll(List.of(1L, 2L));
    }

    @Test
    @Rollback
    @DisplayName("Удалить все связи книги с жанрами")
    void deleteAllRefGenreTest() {
        genreRefBookDao.deleteAllRefGenre(1L);

        Map<Long, List<Long>> result = genreRefBookDao.findByBookIds(List.of(1L));

        assertThat(result).isEmpty();
    }
}
