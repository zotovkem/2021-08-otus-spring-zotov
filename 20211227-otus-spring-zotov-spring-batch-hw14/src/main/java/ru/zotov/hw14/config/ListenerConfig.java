package ru.zotov.hw14.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import ru.zotov.hw14.constant.MigrationTableName;

/**
 * @author Created by ZotovES on 05.01.2022
 * Конфиг лиснеров
 */
@Slf4j
@Configuration
public class ListenerConfig {

    /**
     * Лисенер передает контекст шага в контекст джобы
     */
    @Bean
    public ExecutionContextPromotionListener promotionListener() {
        ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
        listener.setKeys(MigrationTableName.getTableNameArray());

        return listener;
    }

    /**
     * Лиснер логирует пачки данных
     */
    @Bean
    public ChunkListener chunkListener(){
        return new ChunkListener() {
            public void beforeChunk(@NonNull ChunkContext chunkContext) {
                log.info("Начало пачки");
            }

            public void afterChunk(@NonNull ChunkContext chunkContext) {
                log.info("Конец пачки");
            }

            public void afterChunkError(@NonNull ChunkContext chunkContext) {
                log.info("Ошибка пачки");
            }
        };
    }

    /**
     * Лиснер логирует события джобы
     */
    @Bean
    public JobExecutionListener jobExecutionListener(){
        return new JobExecutionListener() {
            @Override
            public void beforeJob(@NonNull JobExecution jobExecution) {
                log.info("Начало миграции");
            }

            @Override
            public void afterJob(@NonNull JobExecution jobExecution) {
                log.info("Конец миграции");
            }
        };
    }
}
