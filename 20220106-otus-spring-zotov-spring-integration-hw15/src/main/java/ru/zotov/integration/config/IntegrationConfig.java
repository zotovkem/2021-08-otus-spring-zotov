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

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(500)
                .errorChannel("myErrors");
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
     * Флоу разработки эпика
     */
    @Bean
    public IntegrationFlow cafeFlow(TeamLeadService teamLeadService, DeveloperService developerService) {
        return IntegrationFlows.from(EPIC_CHANNEL)
                .handle(teamLeadService, SPLIT_EPIC_team_lead_service)
                .split()
                .handle( developerService, DEVELOP_TASK)
                .channel( "foodChannel" )
                .aggregate()
                .get();
    }
}
