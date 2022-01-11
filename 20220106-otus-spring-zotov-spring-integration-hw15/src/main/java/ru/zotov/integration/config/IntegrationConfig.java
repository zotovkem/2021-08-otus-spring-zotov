package ru.zotov.integration.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.zotov.integration.service.DeveloperService;
import ru.zotov.integration.service.TeamLeadService;

import static ru.zotov.integration.constants.Constants.EPIC_CHANNEL;

/**
 * @author Created by ZotovES on 07.01.2022
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan
public class IntegrationConfig {

    protected static final String SPLIT_EPIC_team_lead_service = "splitEpic";
    protected static final String DEVELOP_TASK = "developTask";
    protected static final String TASK_CHANNEL = "taskChannel";

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
    @Qualifier(value = "codeReviewChannel")
    public QueueChannel codeReviewChannel() {
        return MessageChannels.queue(10).get();
    }

    /**
     * Флоу разработки эпика
     */
    @Bean
    public IntegrationFlow epicFlow(TeamLeadService teamLeadService, IntegrationFlow taskFlow) {
        return IntegrationFlows.from(EPIC_CHANNEL)
                .handle(teamLeadService, SPLIT_EPIC_team_lead_service)
                .split()
                .to(taskFlow);
    }

    /**
     * Флоу разработки задачи
     */
    @Bean
    public IntegrationFlow taskFlow(DeveloperService developerService) {
        return IntegrationFlows.from(EPIC_CHANNEL)
                .channel(TASK_CHANNEL)
                .handle(developerService, DEVELOP_TASK)
                .channel("codeReviewChannel")
                .get();
    }
}
