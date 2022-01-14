package ru.zotov.hw16.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.zotov.hw16.dao.BookRepository;

/**
 * @author Created by ZotovES on 07.01.2022
 * Healthcheck проверки наката скриптов миграции
 */
@Component
@RequiredArgsConstructor
public class BooksMigrationScriptAppliedIndicator implements HealthIndicator {
    private final BookRepository bookRepository;

    @Override
    public Health health() {
        return Health
                .status(bookRepository.count() > 0 ? Status.UP : Status.DOWN)
                .build();
    }
}
