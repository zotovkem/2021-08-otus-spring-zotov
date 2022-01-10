package ru.zotov.integration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

/**
 * @author Created by ZotovES on 07.01.2022
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan
public class IntegrationConfig {
}
