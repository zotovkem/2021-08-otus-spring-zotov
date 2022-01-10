package ru.zotov.integration.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.zotov.integration.domain.Epic;

/**
 * @author Created by ZotovES on 10.01.2022
 * Gateway
 */
@MessagingGateway
public interface DeveloperFlowGateway {
    @Gateway(requestChannel = "", replyChannel = "")
    Epic process(Epic epic);
}
