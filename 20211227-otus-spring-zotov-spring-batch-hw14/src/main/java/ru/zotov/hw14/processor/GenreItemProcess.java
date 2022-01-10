package ru.zotov.hw14.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.zotov.hw14.domain.jpa.AuthorJpa;
import ru.zotov.hw14.domain.jpa.GenreJpa;
import ru.zotov.hw14.domain.mongo.AuthorMongo;
import ru.zotov.hw14.domain.mongo.GenreMongo;

import static ru.zotov.hw14.constant.MigrationTableName.AUTHOR;
import static ru.zotov.hw14.constant.MigrationTableName.GENRE;
import static ru.zotov.hw14.utils.BatchJobContextHelper.getContextMapByTableNameEnum;

/**
 * @author Created by ZotovES on 05.01.2022
 * Процессор фильтрует уже существующие записи жанров
 */
@Component
@RequiredArgsConstructor
public class GenreItemProcess implements ItemProcessor<GenreMongo, Pair<String, GenreJpa>> {
    private StepExecution stepExecution;

    @Nullable
    @Override
    public Pair<String, GenreJpa> process(GenreMongo genreMongo) {
        return getContextMapByTableNameEnum(stepExecution, GENRE).containsKey(genreMongo.getId()) ? null :
                Pair.of(genreMongo.getId(), new GenreJpa(null, genreMongo.getName()));
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
