package ru.zotov.hw14.writer;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import ru.zotov.hw14.dao.GenreRepository;
import ru.zotov.hw14.dao.MigrationRegistryRepositoryJpa;
import ru.zotov.hw14.domain.MigrationRegistry;
import ru.zotov.hw14.domain.jpa.GenreJpa;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.zotov.hw14.constant.MigrationTableName.GENRE;
import static ru.zotov.hw14.utils.BatchJobContextHelper.saveStepExecutionContext;

/**
 * @author Created by ZotovES on 04.01.2022
 * Writer жанров
 */
@Component
@RequiredArgsConstructor
public class GenreJpaWriter implements ItemWriter<Pair<String, GenreJpa>> {
    private StepExecution stepExecution;
    private final GenreRepository genreRepository;
    private final MigrationRegistryRepositoryJpa migrationRegistryRepositoryJpa;

    @Override
    public void write(List<? extends Pair<String, GenreJpa>> items) {
        Map<String, GenreJpa> genreJpaMap = items.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

        genreRepository.saveAll(genreJpaMap.values());

        Map<String, Long> genreContextMap = genreJpaMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getId()));
        saveStepExecution(genreContextMap);
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

    private void saveStepExecution(Map<String, Long> addedGenres) {
        addedGenres.entrySet().stream()
                .map(e -> new MigrationRegistry(null, e.getValue(), e.getKey(), GENRE))
                .forEach(migrationRegistryRepositoryJpa::save);
        saveStepExecutionContext(addedGenres, stepExecution, GENRE);
    }
}
