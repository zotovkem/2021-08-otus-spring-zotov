package ru.zotov.carracing.store.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.event.PurchaseTransactionEvent;
import ru.zotov.carracing.store.entity.PurchaseTransaction;
import ru.zotov.carracing.store.enums.ValidateState;
import ru.zotov.carracing.store.service.PurchaseTransactionService;

import static ru.zotov.carracing.common.constant.Constants.KAFKA_GROUP_ID;

/**
 * @author Created by ZotovES on 12.09.2021
 * Слушатель события валидации покупки
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseValidateListener {
    private final PurchaseTransactionService purchaseTransactionService;

    @KafkaListener(topics = Constants.PURCHASE_TRANSACTION_VALIDATE, groupId = KAFKA_GROUP_ID)
    public void onProcess(PurchaseTransactionEvent purchaseTransactionEvent) {
        log.info("Received message " + Constants.PURCHASE_TRANSACTION_VALIDATE);
        purchaseTransactionService.checkValidPayment(mappingToPurchase(purchaseTransactionEvent));
    }

    private PurchaseTransaction mappingToPurchase(PurchaseTransactionEvent purchaseTransactionEvent) {
        return PurchaseTransaction.builder()
                .id(purchaseTransactionEvent.getId())
                .externalId(purchaseTransactionEvent.getExternalId())
                .profileId(purchaseTransactionEvent.getProfileId())
                .token(purchaseTransactionEvent.getToken())
                .validateState(ValidateState.INVALIDATE)
                .build();
    }
}
