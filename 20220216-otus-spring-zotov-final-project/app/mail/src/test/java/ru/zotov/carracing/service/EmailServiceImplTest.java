package ru.zotov.carracing.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.zotov.carracing.event.SendMailEvent;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 23.02.2022
 */
@DisplayName("Тестирование сервиса отправки почты")
@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {
    @Mock private JavaMailSender javaMailSender;
    @Mock private TemplateEngine templateEngine;
    @InjectMocks private EmailServiceImpl emailService;

    @Test
    @DisplayName("Отправка письма")
    void sendMessageTest() {
        emailService.sendMessage(new SendMailEvent("test@mail.ru", "testMail"));

        verify(javaMailSender).send(any(MimeMessagePreparator.class));
    }
}
