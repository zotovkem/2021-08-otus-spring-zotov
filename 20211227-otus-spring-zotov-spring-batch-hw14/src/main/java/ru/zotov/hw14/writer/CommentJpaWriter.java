package ru.zotov.hw14.writer;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import ru.zotov.hw14.dao.CommentRepository;
import ru.zotov.hw14.dao.MigrationRegistryRepository;
import ru.zotov.hw14.domain.MigrationRegistry;
import ru.zotov.hw14.domain.jpa.CommentJpa;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.zotov.hw14.constant.MigrationTableName.COMMENT;
import static ru.zotov.hw14.utils.BatchJobContextHelper.saveStepExecutionContext;

/**
 * @author Created by ZotovES on 04.01.2022
 * Writer книг
 */
@Component
@RequiredArgsConstructor
public class CommentJpaWriter implements ItemWriter<Pair<String, CommentJpa>> {
    private StepExecution stepExecution;
    private final CommentRepository commentRepository;
    private final MigrationRegistryRepository migrationRegistryRepositoryJpa;

    @Override
    public void write(List<? extends Pair<String, CommentJpa>> items) {
        Map<String, CommentJpa> commentJpaMap = items.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

        commentRepository.saveAll(commentJpaMap.values());

        Map<String, Long> commentContextMap = commentJpaMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getId()));
        saveStepExecution(commentContextMap);
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

    private void saveStepExecution(Map<String, Long> addedComments) {
        addedComments.entrySet().stream()
                .map(e -> new MigrationRegistry(null, e.getValue(), e.getKey(), COMMENT))
                .forEach(migrationRegistryRepositoryJpa::save);
        saveStepExecutionContext(addedComments, stepExecution, COMMENT);
    }
}
