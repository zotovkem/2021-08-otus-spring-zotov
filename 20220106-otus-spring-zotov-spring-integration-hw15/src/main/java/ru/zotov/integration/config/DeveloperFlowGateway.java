package ru.zotov.integration.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.zotov.integration.domain.Epic;

import static ru.zotov.integration.constants.Constants.EPIC_CHANNEL;

/**
 * @author Created by ZotovES on 10.01.2022
 * Gateway
 */
@MessagingGateway
public interface DeveloperFlowGateway {
    @Gateway(requestChannel = EPIC_CHANNEL)
    Epic process(Epic epic);
}
