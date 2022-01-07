package ru.zotov.hw14.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.zotov.hw14.domain.jpa.AuthorJpa;
import ru.zotov.hw14.domain.mongo.AuthorMongo;

import static ru.zotov.hw14.constant.MigrationTableName.AUTHOR;
import static ru.zotov.hw14.utils.BatchJobContextHelper.getContextMapByTableNameEnum;

/**
 * @author Created by ZotovES on 05.01.2022
 * Процессор фильтрует уже существующие записи авторов
 */
@Component
@RequiredArgsConstructor
public class AuthorItemProcess implements ItemProcessor<AuthorMongo, Pair<String, AuthorJpa>> {
    private StepExecution stepExecution;

    @Nullable
    @Override
    public Pair<String, AuthorJpa> process(AuthorMongo authorMongo) {
        return getContextMapByTableNameEnum(stepExecution, AUTHOR).containsKey(authorMongo.getId()) ? null :
                Pair.of(authorMongo.getId(), new AuthorJpa(null, authorMongo.getFio()));
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
