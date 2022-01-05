package ru.zotov.hw14.config;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.zotov.hw14.domain.mongo.AuthorMongo;
import ru.zotov.hw14.domain.mongo.BookMongo;
import ru.zotov.hw14.domain.mongo.CommentMongo;
import ru.zotov.hw14.domain.mongo.GenreMongo;

import java.util.Map;

import static ru.zotov.hw14.constant.Constants.*;
import static ru.zotov.hw14.constant.MigrationTableName.*;

/**
 * @author Created by ZotovES on 05.01.2022
 * Конфиг ридеров
 */
@Configuration
public class ReaderConfig {
    /**
     * Reader авторов книг из Mongo
     *
     * @param mongoTemplate mongo template
     */
    @Bean
    public ItemReader<AuthorMongo> authorsReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<AuthorMongo>()
                .template(mongoTemplate)
                .collection(AUTHOR.name().toLowerCase())
                .name(AUTHOR_ITEM_READER)
                .jsonQuery("{}")
                .targetType(AuthorMongo.class)
                .pageSize(CHUNK_SIZE)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    /**
     * Reader жанров книг из Mongo
     *
     * @param mongoTemplate mongo template
     */
    @Bean
    public ItemReader<GenreMongo> genresReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<GenreMongo>()
                .template(mongoTemplate)
                .collection(GENRE.name().toLowerCase())
                .name(GENRE_ITEM_READER)
                .jsonQuery("{}")
                .targetType(GenreMongo.class)
                .pageSize(CHUNK_SIZE)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    /**
     * Reader книг из Mongo
     *
     * @param mongoTemplate mongo template
     */
    @Bean
    public ItemReader<BookMongo> booksReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<BookMongo>()
                .template(mongoTemplate)
                .collection(BOOK.name().toLowerCase())
                .name(BOOK_ITEM_READER)
                .jsonQuery("{}")
                .targetType(BookMongo.class)
                .pageSize(CHUNK_SIZE)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    /**
     * Reader комментариев к книгам из Mongo
     *
     * @param mongoTemplate mongo template
     */
    @Bean
    public ItemReader<CommentMongo> commentsReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<CommentMongo>()
                .template(mongoTemplate)
                .collection(BOOK.name().toLowerCase())
                .name(COMMENT_ITEM_READER)
                .jsonQuery("{}")
                .targetType(CommentMongo.class)
                .pageSize(CHUNK_SIZE)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

}
