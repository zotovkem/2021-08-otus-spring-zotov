package ru.zotov.carracing.store.service;

import org.springframework.lang.NonNull;
import ru.zotov.carracing.event.PurchaseTransactionEvent;
import ru.zotov.carracing.store.entity.PurchaseTransaction;

import java.util.List;

/**
 * @author Created by ZotovES on 11.09.2021
 * Сервис покупок
 */
public interface PurchaseTransactionService {
    /**
     * Создать транзакцию на покупку
     * @param externalId внешний ид
     * @param token токен покупки
     */
    void createPurchaseTransaction(@NonNull String externalId, @NonNull String token);

    /**
     * Получить список всех покупок
     * @return список покупок
     */
    List<PurchaseTransaction> findAllNotCheckedPurchaseTransactions();

    /**
     * Проверить валидность покупки
     * @param purchaseTransaction транзакция покупки
     */
    void checkValidPayment(@NonNull PurchaseTransaction purchaseTransaction);
}
