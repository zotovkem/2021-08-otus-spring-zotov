package ru.zotov.hw14.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import ru.zotov.hw14.domain.jpa.AuthorJpa;
import ru.zotov.hw14.domain.jpa.BookJpa;
import ru.zotov.hw14.domain.jpa.CommentJpa;
import ru.zotov.hw14.domain.jpa.GenreJpa;
import ru.zotov.hw14.domain.mongo.AuthorMongo;
import ru.zotov.hw14.domain.mongo.BookMongo;
import ru.zotov.hw14.domain.mongo.CommentMongo;
import ru.zotov.hw14.domain.mongo.GenreMongo;

import static ru.zotov.hw14.constant.Constants.*;
import static ru.zotov.hw14.constant.Constants.CREATE_JOB_CONTEXT_STEP;

/**
 * @author Created by ZotovES on 05.01.2022
 * Конфиг шагов
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepConfig {
    private final StepBuilderFactory stepBuilderFactory;
    /**
     * Шаг создания контекста
     */
    @Bean
    @Qualifier(value = CREATE_JOB_CONTEXT_STEP)
    public Step stepCreateJobContext(CreateJobContextTasklet createJobContextTasklet) {
        return stepBuilderFactory.get(CREATE_JOB_CONTEXT_STEP).tasklet(createJobContextTasklet).build();
    }

    /**
     * Шаг для переноса авторов
     */
    @Bean
    @Qualifier(value = AUTHOR_MIGRATION_STEP)
    public Step authorStep(ItemReader<AuthorMongo> reader, ItemWriter<Pair<String, AuthorJpa>> writer,
            ItemProcessor<AuthorMongo, Pair<String, AuthorJpa>> itemProcessor, ExecutionContextPromotionListener promotionListener,
            ChunkListener chunkListener) {
        return stepBuilderFactory.get(AUTHOR_MIGRATION_STEP).<AuthorMongo, Pair<String, AuthorJpa>>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(promotionListener)
                .listener(chunkListener)
                .build();
    }

    /**
     * Шаг для переноса жанров
     */
    @Bean
    @Qualifier(value = GENRE_MIGRATION_STEP)
    public Step genreStep(ItemReader<GenreMongo> reader, ItemWriter<Pair<String, GenreJpa>> writer,
            ItemProcessor<GenreMongo, Pair<String, GenreJpa>> itemProcessor, ExecutionContextPromotionListener promotionListener,
            ChunkListener chunkListener) {
        return stepBuilderFactory.get(GENRE_MIGRATION_STEP).<GenreMongo, Pair<String, GenreJpa>>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(promotionListener)
                .listener(chunkListener)
                .build();
    }
    
    /**
     * Шаг для переноса книг
     */
    @Bean
    @Qualifier(value = BOOK_MIGRATION_STEP)
    public Step bookStep(ItemReader<BookMongo> reader, ItemWriter<Pair<String, BookJpa>> writer,
            ItemProcessor<BookMongo, Pair<String, BookJpa>> itemProcessor, ExecutionContextPromotionListener promotionListener,
            ChunkListener chunkListener) {
        return stepBuilderFactory.get(BOOK_MIGRATION_STEP).<BookMongo, Pair<String, BookJpa>>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(promotionListener)
                .listener(chunkListener)
                .build();
    }
    
    /**
     * Шаг для переноса комментарев к книгам
     */
    @Bean
    @Qualifier(value = COMMENT_MIGRATION_STEP)
    public Step commentStep(ItemReader<CommentMongo> reader, ItemWriter<Pair<String, CommentJpa>> writer,
            ItemProcessor<CommentMongo, Pair<String, CommentJpa>> itemProcessor, ExecutionContextPromotionListener promotionListener,
            ChunkListener chunkListener) {
        return stepBuilderFactory.get(COMMENT_MIGRATION_STEP).<CommentMongo, Pair<String, CommentJpa>>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(promotionListener)
                .listener(chunkListener)
                .build();
    }
}
