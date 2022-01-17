package ru.zotov.integration.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.zotov.integration.domain.Bug;
import ru.zotov.integration.domain.PullRequest;
import ru.zotov.integration.domain.Task;
import ru.zotov.integration.service.DeveloperService;
import ru.zotov.integration.service.QaService;
import ru.zotov.integration.service.TeamLeadService;

import static ru.zotov.integration.constants.Constants.*;

/**
 * @author Created by ZotovES on 16.01.2022
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan
public class FlowIntegrationConfig {
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(1000);
    }

    /**
     * Флоу разработки задачи
     */
    @Bean
    public IntegrationFlow epicFlow(TeamLeadService teamLeadService, DeveloperService developerService) {
        return IntegrationFlows.from(EPIC_CHANNEL)
                .handle(teamLeadService, SPLIT_EPIC_TEAM_LEAD_METHOD)
                .split()
                .channel(TASK_CHANNEL)
                .handle(developerService, DEVELOP_TASK_METHOD)
                .publishSubscribeChannel(s -> s
                        .applySequence(true)
                        .subscribe(f -> f.channel(TEAM_LEAD_CODE_REVIEW_CHANNEL))
                        .subscribe(f -> f.channel(DEVELOPER_CODE_REVIEW_CHANNEL))
                )
                .get();
    }

    /**
     * Флоу код ревью тимлида
     */
    @Bean
    public IntegrationFlow teamLeadCodeReviewFlow(TeamLeadService teamLeadService) {
        return IntegrationFlows.from(TEAM_LEAD_CODE_REVIEW_CHANNEL)
                .handle(teamLeadService, TEAMLEAD_CODE_REVIEW_METHOD)
                .channel(DISCUSSION_CHANNEL)
                .get();
    }

    /**
     * Флоу код ревью разработчика
     */
    @Bean
    public IntegrationFlow developerCodeReviewFlow(DeveloperService developerService) {
        return IntegrationFlows.from(DEVELOPER_CODE_REVIEW_CHANNEL)
                .handle(developerService, DEVELOPER_CODE_REVIEW_METHOD)
                .channel(DISCUSSION_CHANNEL)
                .get();
    }

    /**
     * Флоу код ревью разработчика
     */
    @SuppressWarnings("rawtypes")
    @Bean
    public IntegrationFlow discussionFlow(DeveloperService developerService) {
        return IntegrationFlows.from(DISCUSSION_CHANNEL)
                .handle(developerService, DEVELOPER_EDIT_PULL_REQUEST_METHOD)
                .<PullRequest, Class>route(payload -> payload.getTask().getClass(),
                        m -> m.channelMapping(Bug.class, BUG_PULL_REQUEST_CHANNEL)
                                .channelMapping(Task.class, TASK_PULL_REQUEST_CHANNEL))
                .get();
    }

    /**
     * Флоу мерджа пул реквеста на задачу
     */
    @Bean
    public IntegrationFlow taskPullRequestFlow(TeamLeadService teamLeadService,QaService qaService,DeveloperService developerService) {
        return IntegrationFlows.from(TASK_PULL_REQUEST_CHANNEL)
                .aggregate()
                .handle(teamLeadService, TEAMLEAD_TASK_MERGE_PULL_REQUEST_METHOD)
                .aggregate()
                .handle(teamLeadService, TEAMLEAD_SUBMIT_TO_TEST_METHOD)
                .channel(TEST_EPIC_CHANNEL)
                .handle(qaService, QA_TEST_EPIC_METHOD)
                .split()
                .channel(BUG_CHANNEL)
                .handle(developerService, DEVELOPER_FIX_BUG_METHOD)
                .publishSubscribeChannel(s -> s
                        .applySequence(true)
                        .subscribe(f -> f.channel(TEAM_LEAD_CODE_REVIEW_CHANNEL))
                        .subscribe(f -> f.channel(DEVELOPER_CODE_REVIEW_CHANNEL))
                )
                .get();
    }

    /**
     * Флоу мерджа пул реквеста на баг
     */
    @Bean
    public IntegrationFlow bugPullRequestFlow(TeamLeadService teamLeadService, QaService qaService) {
        return IntegrationFlows.from(BUG_PULL_REQUEST_CHANNEL)
                .aggregate()
                .handle(teamLeadService, TEAMLEAD_BUG_MERGE_PULL_REQUEST_METHOD)
                .channel(TEST_BUG_CHANNEL)
                .handle(qaService, QA_TEST_BUG_METHOD)
                .aggregate()
                .channel(DEPLOY_CHANNEL)
                .get();
    }
    /**
     * Флоу деплоя эпика
     */
    @Bean
    public IntegrationFlow deployFlow(TeamLeadService teamLeadService) {
        return IntegrationFlows.from(DEPLOY_CHANNEL)
                .handle(teamLeadService, TEAMLEAD_DEPLOY_METHOD)
                .get();
    }
}
