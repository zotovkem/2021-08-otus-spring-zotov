package ru.zotov.hw7.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.zotov.hw7.domain.Comment;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Created by ZotovES on 22.10.2021
 */
@DataJpaTest
@DisplayName("Тестирование репозитория комментариев к книге")
class CommentRepositoryJpaImplTest {
    @Autowired private CommentRepository commentRepository;

    @Test
    @DisplayName("Поиск по ид книги")
    void findByBookId() {
        List<Comment> result = commentRepository.findByBookId(1L);

        assertThat(result).asList().hasSize(2)
                .anySatisfy(comment ->
                        assertThat(comment).hasFieldOrPropertyWithValue("id", 1L)
                                .hasFieldOrPropertyWithValue("content", "Вроде не чего, еще не дочитал")
                                .hasFieldOrPropertyWithValue("author", "ЗотовЕС"))
                .anySatisfy(comment ->
                        assertThat(comment).hasFieldOrPropertyWithValue("id", 3L)
                                .hasFieldOrPropertyWithValue("content", "Комментарий")
                                .hasFieldOrPropertyWithValue("author", "Иванов"));
    }
}
