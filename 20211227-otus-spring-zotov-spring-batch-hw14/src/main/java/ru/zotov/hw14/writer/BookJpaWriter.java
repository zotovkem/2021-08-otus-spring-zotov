package ru.zotov.hw14.writer;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import ru.zotov.hw14.dao.BookRepositoryJpa;
import ru.zotov.hw14.dao.MigrationRegistryRepositoryJpa;
import ru.zotov.hw14.domain.MigrationRegistry;
import ru.zotov.hw14.domain.jpa.BookJpa;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.zotov.hw14.constant.MigrationTableName.BOOK;
import static ru.zotov.hw14.constant.MigrationTableName.GENRE;
import static ru.zotov.hw14.utils.BatchJobContextHelper.saveStepExecutionContext;

/**
 * @author Created by ZotovES on 04.01.2022
 * Writer книг
 */
@Component
@RequiredArgsConstructor
public class BookJpaWriter implements ItemWriter<Pair<String, BookJpa>> {
    private StepExecution stepExecution;
    private final BookRepositoryJpa bookRepository;
    private final MigrationRegistryRepositoryJpa migrationRegistryRepositoryJpa;

    @Override
    public void write(List<? extends Pair<String, BookJpa>> items) {
        Map<String, BookJpa> bookJpaMap = items.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

        bookRepository.saveAll(bookJpaMap.values());

        Map<String, Long> bookContextMap = bookJpaMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getId()));
        saveStepExecution(bookContextMap);
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

    private void saveStepExecution(Map<String, Long> addedBooks) {
        addedBooks.entrySet().stream()
                .map(e -> new MigrationRegistry(null, e.getValue(), e.getKey(), BOOK))
                .forEach(migrationRegistryRepositoryJpa::save);
        saveStepExecutionContext(addedBooks, stepExecution, BOOK);
    }
}
