package ru.zotov.hw14.constant;

/**
 * @author Created by ZotovES on 04.01.2022
 */
public class Constants {
    public static final int CHUNK_SIZE = 5;
    public static final String JOB_NAME = "bookMigrationMongoToH2";
    public static final String CREATE_JOB_CONTEXT_STEP = "stepCreateJobContext";
    public static final String AUTHOR_MIGRATION_STEP = "authorStep";
    public static final String GENRE_MIGRATION_STEP = "genreStep";
    public static final String BOOK_MIGRATION_STEP = "bookStep";
    public static final String COMMENT_MIGRATION_STEP = "commentStep";
    public static final String AUTHOR_ITEM_READER = "authorItemReader";
    public static final String GENRE_ITEM_READER = "genreItemReader";
    public static final String BOOK_ITEM_READER = "bookItemReader";
    public static final String COMMENT_ITEM_READER = "commentItemReader";
}
