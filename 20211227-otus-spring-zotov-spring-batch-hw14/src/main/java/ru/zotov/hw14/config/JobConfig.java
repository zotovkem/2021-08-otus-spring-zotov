package ru.zotov.hw14.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static ru.zotov.hw14.constant.Constants.*;

/**
 * Конфиг джобы
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;

    /**
     * Джоба миграции данных
     */
    @Bean
    public Job migrationAuthorJob(Map<String, Step> stepMap, JobExecutionListener jobExecutionListener) {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(stepMap.get(CREATE_JOB_CONTEXT_STEP))
                .next(stepMap.get(AUTHOR_MIGRATION_STEP))
                .next(stepMap.get(GENRE_MIGRATION_STEP))
                .next(stepMap.get(BOOK_MIGRATION_STEP))
                .next(stepMap.get(COMMENT_MIGRATION_STEP))
                .end()
                .listener(jobExecutionListener)
                .build();
    }
}
