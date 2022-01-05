package ru.zotov.hw14.writer;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import ru.zotov.hw14.dao.AuthorRepository;
import ru.zotov.hw14.dao.MigrationRegistryRepository;
import ru.zotov.hw14.domain.MigrationRegistry;
import ru.zotov.hw14.domain.jpa.AuthorJpa;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.zotov.hw14.constant.MigrationTableName.AUTHOR;
import static ru.zotov.hw14.utils.BatchJobContextHelper.saveStepExecutionContext;

/**
 * @author Created by ZotovES on 04.01.2022
 * Writer авторов
 */
@Component
@RequiredArgsConstructor
public class AuthorJpaWriter implements ItemWriter<Pair<String, AuthorJpa>> {
    private StepExecution stepExecution;
    private final AuthorRepository authorRepositoryJpa;
    private final MigrationRegistryRepository migrationRegistryRepositoryJpa;

    @Override
    public void write(List<? extends Pair<String, AuthorJpa>> items) {
        Map<String, AuthorJpa> authorJpaMap = items.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

        authorRepositoryJpa.saveAll(authorJpaMap.values());

        Map<String, Long> authorContextMap = authorJpaMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getId()));
        saveStepExecution(authorContextMap);
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

    private void saveStepExecution(Map<String, Long> addedAuthors) {
        addedAuthors.entrySet().stream()
                .map(e -> new MigrationRegistry(null, e.getValue(), e.getKey(), AUTHOR))
                .forEach(migrationRegistryRepositoryJpa::save);

        saveStepExecutionContext(addedAuthors, stepExecution, AUTHOR);
    }
}
