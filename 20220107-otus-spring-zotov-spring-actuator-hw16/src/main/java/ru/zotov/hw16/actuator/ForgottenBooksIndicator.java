package ru.zotov.hw16.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.zotov.hw16.dao.BookRepository;
import ru.zotov.hw16.domain.Book;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 07.01.2022
 * Healthcheck проверки на не цензурные слова в комментариях книги
 */
@Component
@RequiredArgsConstructor
public class ForgottenBooksIndicator implements HealthIndicator {
    private final BookRepository bookRepository;

    @Override
    public Health health() {
        List<String> booksName = bookRepository.findByCommentContent("плохое слово").stream()
                .map(Book::getName)
                .collect(Collectors.toList());
        if (booksName.isEmpty()) {
            return Health.up()
                    .withDetails(Map.of("censured", List.of()))
                    .build();
        }

        return Health.down()
                .withDetails(Map.of("censured", booksName))
                .build();
    }
}
