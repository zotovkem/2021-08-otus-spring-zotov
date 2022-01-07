package ru.zotov.hw14.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.zotov.hw14.domain.jpa.AuthorJpa;
import ru.zotov.hw14.domain.jpa.BookJpa;
import ru.zotov.hw14.domain.jpa.GenreJpa;
import ru.zotov.hw14.domain.mongo.BookMongo;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.zotov.hw14.constant.MigrationTableName.*;
import static ru.zotov.hw14.utils.BatchJobContextHelper.getContextMapByTableNameEnum;

/**
 * @author Created by ZotovES on 05.01.2022
 * Процессор фильтрует уже существующие записи книг
 */
@Component
@RequiredArgsConstructor
public class BookItemProcess implements ItemProcessor<BookMongo, Pair<String, BookJpa>> {
    private StepExecution stepExecution;

    @Nullable
    @Override
    public Pair<String, BookJpa> process(BookMongo bookMongo) {
        if (getContextMapByTableNameEnum(stepExecution, BOOK).containsKey(bookMongo.getId())){
            return null;
        }

        Map<String, Long> mappingAuthorIds = getContextMapByTableNameEnum(stepExecution, AUTHOR);
        Map<String, Long> mappingGenreIds = getContextMapByTableNameEnum(stepExecution, GENRE);
        Set<AuthorJpa> authors = bookMongo.getAuthors().stream()
                .map(a -> new AuthorJpa(mappingAuthorIds.get(a.getId()), a.getFio()))
                .collect(Collectors.toSet());
        Set<GenreJpa> genres = bookMongo.getGenres().stream()
                .map(g -> new GenreJpa(mappingGenreIds.get(g.getId()), g.getName()))
                .collect(Collectors.toSet());

        return  Pair.of(bookMongo.getId(), BookJpa.builder()
                        .name(bookMongo.getName())
                        .releaseYear(bookMongo.getReleaseYear())
                        .authors(authors)
                        .genres(genres)
                        .build());
    }

    /**
     * Инициализация контекста шага
     *
     * @param stepExecution контекст шага
     */
    @BeforeStep
    public void initStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
