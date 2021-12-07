package ru.zotov.hw11.config;

import com.mongodb.reactivestreams.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

/**
 * @author Created by ZotovES on 27.11.2021
 */
@Configuration
@RequiredArgsConstructor
public class ReactiveMongoConfig {
    private final MongoClient mongoClient;

    /**
     * Реактивный MongoTemplate
     */
    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(@Value("${spring.data.mongodb.database}") String mongoDbName) {
        return new ReactiveMongoTemplate(mongoClient, mongoDbName);
    }

    /**
     * Маппер из энтити в дто и обратно
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
