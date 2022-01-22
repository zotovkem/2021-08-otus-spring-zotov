package ru.zotov.integration.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.*;

import static ru.zotov.integration.constants.Constants.*;

/**
 * @author Created by ZotovES on 16.01.2022
 * Конфигурация каналов
 */
@Configuration
public class ChannelIntegrationConfig {
    /**
     * Канал для эпиков
     */
    @Bean
    @Qualifier(value = EPIC_CHANNEL)
    public QueueChannel epicChannel() {
        return MessageChannels.queue(10).get();
    }
    /**
     * Канал для задач
     */
    @Bean
    @Qualifier(value = TASK_CHANNEL)
    public QueueChannel taskChannel() {
        return MessageChannels.queue(20).get();
    }

    /**
     * Канал для пул реквестов задач
     */
    @Bean
    @Qualifier(value = TASK_PULL_REQUEST_CHANNEL)
    public QueueChannel taskPullRequestChannel() {
        return MessageChannels.queue(20).get();
    }

    /**
     * Канал для пул реквестов багов
     */
    @Bean
    @Qualifier(value = BUG_PULL_REQUEST_CHANNEL)
    public QueueChannel bugPullRequestChannel() {
        return MessageChannels.queue(40).get();
    }

    /**
     * Канал для замечаний по код ревью
     */
    @Bean
    @Qualifier(value = DISCUSSION_CHANNEL)
    public QueueChannel discussionChannel() {
        return MessageChannels.queue(80).get();
    }

    /**
     * Канал код ревью разработчика
     */
    @Bean
    @Qualifier(value = DEVELOPER_CODE_REVIEW_CHANNEL)
    public QueueChannel developerCodeReviewChannel() {
        return MessageChannels.queue(20).get();
    }

    /**
     * Канал код ревью тимлида
     */
    @Bean
    @Qualifier(value = TEAM_LEAD_CODE_REVIEW_CHANNEL)
    public QueueChannel teamLeadCodeReviewChannel() {
        return MessageChannels.queue(20).get();
    }

    /**
     * Канал тестирования задачи
     */
    @Bean
    @Qualifier(value = TEST_BUG_CHANNEL)
    public QueueChannel testChannel() {
        return MessageChannels.queue(40).get();
    }

    /**
     * Канал для багов
     */
    @Bean
    @Qualifier(value = BUG_CHANNEL)
    public QueueChannel bugChannel() {
        return MessageChannels.queue(40).get();
    }

    /**
     * Канал деплоя  задачи
     */
    @Bean
    @Qualifier(value = DEPLOY_CHANNEL)
    public QueueChannel deployChannel() {
        return MessageChannels.queue(10).get();
    }

    /**
     * Канал тестирования бага
     */
    @Bean
    @Qualifier(value = TEST_BUG_CHANNEL)
    public QueueChannel testBugChannel() {
        return MessageChannels.queue(10).get();
    }
}
