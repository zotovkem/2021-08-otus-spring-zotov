package ru.zotov.carracing.store.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import ru.zotov.carracing.event.PurchaseFuelAddEvent;
import ru.zotov.carracing.event.PurchaseTransactionEvent;
import ru.zotov.carracing.security.filter.CustomUser;
import ru.zotov.carracing.security.utils.SecurityService;
import ru.zotov.carracing.store.dto.ResponsePaymentCheckedIntegrationDto;
import ru.zotov.carracing.store.entity.Product;
import ru.zotov.carracing.store.entity.PurchaseTransaction;
import ru.zotov.carracing.store.enums.ValidateState;
import ru.zotov.carracing.store.integration.GoogleStoreIntegration;
import ru.zotov.carracing.store.repo.ProductRepository;
import ru.zotov.carracing.store.repo.PurchaseTransactionRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static ru.zotov.carracing.common.constant.Constants.PURCHASE_FUEL_ADD;
import static ru.zotov.carracing.common.constant.Constants.PURCHASE_TRANSACTION_VALIDATE;

/**
 * @author Created by ZotovES on 26.02.2022
 */
@DisplayName("Тестирование сервиса валидации покупок")
@ExtendWith(MockitoExtension.class)
class PurchaseTransactionServiceImplTest {
    @Mock private GoogleStoreIntegration googleStoreIntegration;
    @Mock private PurchaseTransactionRepository purchaseTransactionRepository;
    @Mock private ProductRepository productRepository;
    @Mock private KafkaTemplate<PurchaseTransaction, Object> kafkaTemplate;
    @Mock private SecurityService securityService;
    @InjectMocks private PurchaseTransactionServiceImpl purchaseTransactionService;

    @Test
    @DisplayName("Создать покупку")
    void createPurchaseTransactionTest() {
        CustomUser user = new CustomUser(UUID.randomUUID().toString(), "testUser", "pass", Collections.emptyList());
        Product product = Product.builder()
                .id(1L)
                .count(1)
                .build();
        when(productRepository.findByExternalId(anyString())).thenReturn(Optional.of(product));
        when(purchaseTransactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(securityService.getUserTh()).thenReturn(user);

        purchaseTransactionService.createPurchaseTransaction("testId", "testPurchaseToken");

        verify(productRepository).findByExternalId(anyString());
        verify(purchaseTransactionRepository).save(any());
        verify(securityService).getUserTh();
        verify(kafkaTemplate).send(eq(PURCHASE_TRANSACTION_VALIDATE), any(PurchaseTransactionEvent.class));
        verify(kafkaTemplate).send(eq(PURCHASE_FUEL_ADD), any(PurchaseFuelAddEvent.class));
    }

    @Test
    @DisplayName("Получить все покупки ожидающие проверки")
    void findAllNotCheckedPurchaseTransactionsTest() {
        PurchaseTransaction purchaseTransaction = PurchaseTransaction.builder().build();
        when(purchaseTransactionRepository.findByValidateStateIn(anySet())).thenReturn(List.of(purchaseTransaction));

        List<PurchaseTransaction> result = purchaseTransactionService.findAllNotCheckedPurchaseTransactions();

        assertThat(result).asList().isNotEmpty();

        verify(purchaseTransactionRepository).findByValidateStateIn(anySet());
    }

    @Test
    @DisplayName("Успешная проверка валидности покупки")
    void checkValidPaymentTest() {
        PurchaseTransaction purchaseTransaction = PurchaseTransaction.builder()
                .id(1L)
                .profileId(UUID.randomUUID().toString())
                .validateState(ValidateState.WAITING)
                .build();
        ResponsePaymentCheckedIntegrationDto checkedIntegrationDto = ResponsePaymentCheckedIntegrationDto.builder()
                .resultChecked(true)
                .build();
        when(googleStoreIntegration.checkPayment(any())).thenReturn(checkedIntegrationDto);
        when(purchaseTransactionRepository.findById(anyLong())).thenReturn(Optional.of(purchaseTransaction));

        purchaseTransactionService.checkValidPayment(purchaseTransaction);

        verify(purchaseTransactionRepository).save(any());
        verify(purchaseTransactionRepository).findById(anyLong());
        verify(kafkaTemplate, never()).send(eq(PURCHASE_FUEL_ADD), any(PurchaseFuelAddEvent.class));
        verify(kafkaTemplate, never()).send(eq(PURCHASE_TRANSACTION_VALIDATE), any(PurchaseTransactionEvent.class));
    }

    @Test
    @DisplayName("Не успешная проверка валидности покупки")
    void checkInvalidPaymentTest() {
        PurchaseTransaction purchaseTransaction = PurchaseTransaction.builder()
                .id(1L)
                .profileId(UUID.randomUUID().toString())
                .validateState(ValidateState.WAITING)
                .product(Product.builder()
                        .id(1L)
                        .count(1)
                        .build())
                .build();
        ResponsePaymentCheckedIntegrationDto checkedIntegrationDto = ResponsePaymentCheckedIntegrationDto.builder()
                .resultChecked(false)
                .build();
        when(googleStoreIntegration.checkPayment(any())).thenReturn(checkedIntegrationDto);
        when(purchaseTransactionRepository.findById(anyLong())).thenReturn(Optional.of(purchaseTransaction));

        purchaseTransactionService.checkValidPayment(purchaseTransaction);

        verify(purchaseTransactionRepository).save(any());
        verify(purchaseTransactionRepository).findById(anyLong());
        verify(kafkaTemplate).send(eq(PURCHASE_FUEL_ADD), any(PurchaseFuelAddEvent.class));
        verify(kafkaTemplate, never()).send(eq(PURCHASE_TRANSACTION_VALIDATE), any(PurchaseTransactionEvent.class));
    }

    @Test
    @DisplayName("Отправка на повторную проверку")
    void retryValidatePaymentTest() {
        PurchaseTransaction purchaseTransaction = PurchaseTransaction.builder()
                .id(1L)
                .profileId(UUID.randomUUID().toString())
                .validateState(ValidateState.WAITING)
                .product(Product.builder()
                        .id(1L)
                        .count(1)
                        .build())
                .build();
        when(googleStoreIntegration.checkPayment(any())).thenReturn(null);
        when(purchaseTransactionRepository.findById(anyLong())).thenReturn(Optional.of(purchaseTransaction));

        purchaseTransactionService.checkValidPayment(purchaseTransaction);

        verify(purchaseTransactionRepository,never()).save(any());
        verify(purchaseTransactionRepository).findById(anyLong());
        verify(kafkaTemplate, never()).send(eq(PURCHASE_FUEL_ADD), any(PurchaseFuelAddEvent.class));
        verify(kafkaTemplate).send(eq(PURCHASE_TRANSACTION_VALIDATE), any(PurchaseTransactionEvent.class));
    }
}
