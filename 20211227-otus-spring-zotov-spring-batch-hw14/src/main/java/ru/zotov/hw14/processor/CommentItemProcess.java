package ru.zotov.hw14.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.zotov.hw14.domain.jpa.BookJpa;
import ru.zotov.hw14.domain.jpa.CommentJpa;
import ru.zotov.hw14.domain.mongo.CommentMongo;
import ru.zotov.hw14.domain.mongo.CommentProjectionMongo;

import static ru.zotov.hw14.constant.MigrationTableName.BOOK;
import static ru.zotov.hw14.constant.MigrationTableName.COMMENT;
import static ru.zotov.hw14.utils.BatchJobContextHelper.getContextMapByTableNameEnum;

/**
 * @author Created by ZotovES on 05.01.2022
 * Процессор фильтрует уже существующие записи комментариев к книгам
 */
@Component
@RequiredArgsConstructor
public class CommentItemProcess implements ItemProcessor<CommentProjectionMongo, Pair<String, CommentJpa>> {
    private StepExecution stepExecution;

    @Nullable
    @Override
    public Pair<String, CommentJpa> process(CommentProjectionMongo commentMongo) {
        if (getContextMapByTableNameEnum(stepExecution, COMMENT).containsKey(commentMongo.getId())) {
            return null;
        }

        Long bookId = getContextMapByTableNameEnum(stepExecution, BOOK).get(commentMongo.getBook().getId());
        BookJpa bookJpa = BookJpa.builder().id(bookId).build();

        return Pair.of(commentMongo.getId(), new CommentJpa(null, bookJpa, commentMongo.getContent(),commentMongo.getAuthor(),
                commentMongo.getCreateDate()));
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
