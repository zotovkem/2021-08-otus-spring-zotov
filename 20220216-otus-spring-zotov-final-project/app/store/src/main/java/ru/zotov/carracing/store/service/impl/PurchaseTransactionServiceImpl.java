package ru.zotov.carracing.store.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.carracing.event.PurchaseFuelAddEvent;
import ru.zotov.carracing.event.PurchaseTransactionEvent;
import ru.zotov.carracing.security.utils.SecurityService;
import ru.zotov.carracing.store.dto.RequestPaymentCheckedDto;
import ru.zotov.carracing.store.entity.Product;
import ru.zotov.carracing.store.entity.PurchaseTransaction;
import ru.zotov.carracing.store.enums.ValidateState;
import ru.zotov.carracing.store.integration.GoogleStoreIntegration;
import ru.zotov.carracing.store.repo.ProductRepository;
import ru.zotov.carracing.store.repo.PurchaseTransactionRepository;
import ru.zotov.carracing.store.service.PurchaseTransactionService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static ru.zotov.carracing.common.constant.Constants.PURCHASE_FUEL_ADD;
import static ru.zotov.carracing.common.constant.Constants.PURCHASE_TRANSACTION_VALIDATE;

/**
 * @author Created by ZotovES on 11.09.2021
 * Реализация сервиса покупок
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseTransactionServiceImpl implements PurchaseTransactionService {
    private final GoogleStoreIntegration googleStoreIntegration;
    private final PurchaseTransactionRepository purchaseTransactionRepository;
    private final ProductRepository productRepository;
    private final KafkaTemplate<PurchaseTransaction, Object> kafkaTemplate;
    private final SecurityService securityService;

    /**
     * Создать транзакцию на покупку
     *
     * @param externalId внешний ид
     * @param token      токен покупки
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPurchaseTransaction(@NonNull String externalId, @NonNull String token) {
        Optional<Product> product = productRepository.findByExternalId(externalId);
        PurchaseTransaction purchaseTransaction = product
                .map(buildProductPurchaseTransactionFunction(token))
                .orElseThrow();

        PurchaseTransaction savedPurchase = purchaseTransactionRepository.save(purchaseTransaction);

        PurchaseTransactionEvent purchaseTransactionEvent = PurchaseTransactionEvent.builder()
                .id(savedPurchase.getId())
                .externalId(externalId)
                .token(token)
                .profileId(purchaseTransaction.getProfileId())
                .build();

        kafkaTemplate.send(PURCHASE_TRANSACTION_VALIDATE, purchaseTransactionEvent);

        PurchaseFuelAddEvent fuelAddEvent = PurchaseFuelAddEvent.builder()
                .fuel(product.map(Product::getCount).orElseThrow())
                .profileId(purchaseTransaction.getProfileId()).build();

        kafkaTemplate.send(PURCHASE_FUEL_ADD, fuelAddEvent);
    }

    /**
     * Получить список всех покупок
     *
     * @return список покупок
     */
    @Override
    @Transactional(readOnly = true)
    public List<PurchaseTransaction> findAllNotCheckedPurchaseTransactions() {
        return purchaseTransactionRepository.findByValidateStateIn(Set.of(ValidateState.ERROR_TRY, ValidateState.WAITING));
    }

    /**
     * Проверить валидность покупки
     *
     * @param purchaseTransaction транзакция покупки
     */
    @Override
    @Retryable(value = FeignException.class, maxAttempts = 2, backoff = @Backoff(delay = 1000))
    @Transactional(rollbackFor = Exception.class)
    public void checkValidPayment(@NonNull PurchaseTransaction purchaseTransaction) {
        log.info("Попытка проверить покупку {} в GoogleStore", purchaseTransaction.getExternalId());
        var resultDto = googleStoreIntegration.checkPayment(getRequestPaymentCheckedDto(purchaseTransaction));
        var persistPurchaseTransaction = purchaseTransactionRepository.findById(purchaseTransaction.getId());
        if (resultDto == null) {
            persistPurchaseTransaction.filter(pt -> ValidateState.WAITING.equals(pt.getValidateState()))
                    .ifPresent(returnPurchaseTransactionEvent ->
                            kafkaTemplate.send(PURCHASE_TRANSACTION_VALIDATE, buildPurchaseTransactionEvent(returnPurchaseTransactionEvent)));
            return;
        }
        persistPurchaseTransaction.ifPresent(purchase -> {
            purchase.setValidateState(resultDto.getResultChecked() ? ValidateState.VALIDATE : ValidateState.INVALIDATE);
            purchaseTransactionRepository.save(purchase);
            if (!resultDto.getResultChecked()) {
                log.info("Проверка покупки прошла не успешно. Списываем топливо");
                PurchaseFuelAddEvent fuelAddEvent = PurchaseFuelAddEvent.builder()
                        .fuel(-purchase.getProduct().getCount())
                        .profileId(purchaseTransaction.getProfileId())
                        .build();

                kafkaTemplate.send(PURCHASE_FUEL_ADD, fuelAddEvent);
            }
        });
    }

    private PurchaseTransactionEvent buildPurchaseTransactionEvent(PurchaseTransaction purchaseTransactionEvent) {
        return PurchaseTransactionEvent.builder()
                .id(purchaseTransactionEvent.getId())
                .externalId(purchaseTransactionEvent.getProduct().getExternalId())
                .token(purchaseTransactionEvent.getToken())
                .build();
    }

    private RequestPaymentCheckedDto getRequestPaymentCheckedDto(PurchaseTransaction purchaseTransaction) {
        return RequestPaymentCheckedDto.builder()
                .id(purchaseTransaction.getExternalId())
                .token(purchaseTransaction.getToken())
                .build();
    }

    private Function<Product, PurchaseTransaction> buildProductPurchaseTransactionFunction(String token) {
        return product -> PurchaseTransaction.builder()
                .token(token)
                .profileId(securityService.getUserTh().getId())
                .count(product.getCount())
                .purchaseDate(ZonedDateTime.now())
                .product(product)
                .validateState(ValidateState.WAITING)
                .build();
    }
}
