package ru.zotov.hw16.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import ru.zotov.hw16.domain.Book;

/**
 * @author Created by ZotovES on 07.01.2022
 * Healthcheck проверки наката скриптов миграции
 */
@Component
@RequiredArgsConstructor
public class BooksMigrationScriptAppliedIndicator implements HealthIndicator {
    private final MongoTemplate mongoTemplate;

    @Override
    public Health health() {
        return Health
                .status(mongoTemplate.collectionExists(Book.class) ? Status.UP : Status.DOWN)
                .build();
    }
}
