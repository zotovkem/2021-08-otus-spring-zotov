package ru.zotov.integration.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.zotov.integration.domain.Bug;
import ru.zotov.integration.domain.PullRequest;
import ru.zotov.integration.service.DeveloperService;
import ru.zotov.integration.service.QaService;
import ru.zotov.integration.service.TeamLeadService;

import java.util.List;

import static ru.zotov.integration.constants.Constants.EPIC_CHANNEL;

/**
 * @author Created by ZotovES on 07.01.2022
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan
public class IntegrationConfig {

    protected static final String SPLIT_EPIC_TEAM_LEAD_SERVICE = "splitEpic";
    protected static final String DEVELOP_TASK_METHOD = "developTask";

    protected static final String TASK_CHANNEL = "taskChannel";
    protected static final String DISCUSSION_CHANNEL = "discussionChannel";
    private static final String PULL_REQUEST_CHANNEL = "pullRequestChannel";
    protected static final String CODE_REVIEW_CHANNEL = "codeReviewChannel";
    protected static final String BUG_CHANNEL = "bugChannel";

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(1000);
    }

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
        return MessageChannels.queue(10).get();
    }

    /**
     * Канал код ревью
     */
    @Bean
    @Qualifier(value = CODE_REVIEW_CHANNEL)
    public QueueChannel codeReviewChannel() {
        return MessageChannels.queue(10).get();
    }

    /**
     * Канал для пул реквестов
     */
    @Bean
    @Qualifier(value = PULL_REQUEST_CHANNEL)
    public QueueChannel pullRequestChannel() {
        return MessageChannels.queue(10).get();
    }

    /**
     * Канал для замечаний по код ревью
     */
    @Bean
    @Qualifier(value = DISCUSSION_CHANNEL)
    public QueueChannel discussionChannel() {
        return MessageChannels.queue(10).get();
    }

    /**
     * Канал мерджа ветки
     */
    @Bean
    @Qualifier(value = "mergeChannel")
    public QueueChannel mergeChannel() {
        return MessageChannels.queue(10).get();
    }

    /**
     * Канал код ревью разработчика
     */
    @Bean
    @Qualifier(value = "developerCodeReviewChannel")
    public QueueChannel developerCodeReviewChannel() {
        return MessageChannels.queue(10).get();
    }

    /**
     * Канал код ревью тимлида
     */
    @Bean
    @Qualifier(value = "teamLeadCodeReviewChannel")
    public QueueChannel teamLeadCodeReviewChannel() {
        return MessageChannels.queue(10).get();
    }

    /**
     * Канал тестирования задачи
     */
    @Bean
    @Qualifier(value = "testChannel")
    public QueueChannel testChannel() {
        return MessageChannels.queue(10).get();
    }

    /**
     * Канал для багов
     */
    @Bean
    @Qualifier(value = BUG_CHANNEL)
    public QueueChannel bugChannel() {
        return MessageChannels.queue(10).get();
    }

    /**
     * Канал деплоя  задачи
     */
    @Bean
    @Qualifier(value = "deployChannel")
    public QueueChannel deployChannel() {
        return MessageChannels.queue(10).get();
    }

    /**
     * Флоу описания эпика
     */
    @Bean
    public IntegrationFlow epicFlow(TeamLeadService teamLeadService) {
        return IntegrationFlows.from(EPIC_CHANNEL)
                .handle(teamLeadService, SPLIT_EPIC_TEAM_LEAD_SERVICE)
                .split()
                .channel(TASK_CHANNEL)
                .get();
    }

    /**
     * Флоу разработки задачи
     */
    @Bean
    public IntegrationFlow developerFlow(DeveloperService developerService) {
        return flow -> flow
                .channel(TASK_CHANNEL)
                .handle(developerService, DEVELOP_TASK_METHOD)
                .channel(CODE_REVIEW_CHANNEL);
    }

    /**
     * Флоу код ревью задачи
     */
    @Bean
    public IntegrationFlow codeReviewFlow() {
        return flow -> flow
                .channel(CODE_REVIEW_CHANNEL)
                .publishSubscribeChannel(s -> s
                        .applySequence(true)
                        .subscribe(f -> f.channel(teamLeadCodeReviewChannel()))
                        .subscribe(f -> f.channel(developerCodeReviewChannel()))
                );
    }

    /**
     * Флоу код ревью тимлида
     */
    @Bean
    public IntegrationFlow teamLeadCodeReviewFlow(TeamLeadService teamLeadService) {
        return IntegrationFlows.from("teamLeadCodeReviewChannel")
                .handle(teamLeadService, "codeReview")
                .split()
                .channel(DISCUSSION_CHANNEL)
                .get();
    }

    /**
     * Флоу код ревью разработчика
     */
    @Bean
    public IntegrationFlow developerCodeReviewFlow(DeveloperService developerService) {
        return IntegrationFlows.from("developerCodeReviewChannel")
                .handle(developerService, "codeReview")
                .split()
                .channel(DISCUSSION_CHANNEL)
                .get();
    }

    /**
     * Флоу код ревью разработчика
     */
    @Bean
    public IntegrationFlow discussionFlow(DeveloperService developerService) {
        return IntegrationFlows.from(DISCUSSION_CHANNEL)
                .handle(developerService, "editPullRequest")
                .aggregate()
                .channel(PULL_REQUEST_CHANNEL)
                .get();
    }

    /**
     * Флоу работу с пул реквестом
     */
    @Bean
    public IntegrationFlow pullRequestFlow(TeamLeadService teamLeadService) {
        return IntegrationFlows.from(PULL_REQUEST_CHANNEL)
                .channel("mergeChannel")
                .handle(teamLeadService,"mergePullRequest")
                .aggregate().aggregate()
                .channel("testChannel")
                .get();
    }

    /**
     * Флоу тестирования задачи
     */
    @Bean
    public IntegrationFlow testFlow(QaService qaService) {
        return IntegrationFlows.from("testChannel")
                .handle(qaService, "testEpic")
                .split()
                .channel(BUG_CHANNEL)
                .handle(qaService, "testBug")
                .aggregate()
                .channel("deployChannel")
                .get();
    }

    /**
     * Флоу фикса багов
     */
    @Bean
    public IntegrationFlow bugfixFlow(DeveloperService developerService) {
        return IntegrationFlows.from(BUG_CHANNEL)
                .handle(developerService, "fixBug")
                .channel(CODE_REVIEW_CHANNEL)
                .get();
    }

    /**
     * Флоу деплоя эпика
     */
    @Bean
    public IntegrationFlow deployFlow(TeamLeadService teamLeadService) {
        return IntegrationFlows.from("deployChannel")
                .handle(teamLeadService, "deploy")
                .get();
    }
}
