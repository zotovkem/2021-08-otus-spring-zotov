package ru.zotov.wallet.service;

import org.springframework.lang.NonNull;
import ru.zotov.wallet.entity.Wallet;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Created by ZotovES on 23.08.2021
 * Сервис управления кошельком игрока
 */
public interface WalletService {
    /**
     * Расход топлива
     *
     * @param profileId ид профиля игрока
     * @param fuel      кол-во топлива
     */
    void expandFuel(@NonNull UUID profileId, @NonNull Integer fuel, @NonNull Long raceId);
    /**
     * Добавить топливо
     *
     * @param profileId внешний ид провиля игрока
     * @param fuel      кол-во топлива
     */
    void addFuel(@NonNull UUID profileId, @NonNull Integer fuel);
    /**
     * Создать кошелек
     * @param profileId внешний ид игрока
     */
    void createWallet(@NonNull UUID profileId);
    /**
     * Получить кошелек игрока
     *
     * @return кошелек игрока
     */
    Optional<Wallet> getWallet();
    /**
     * Выдать награду
     *
     * @param profileId внешний ид игрока
     * @param rewardId  ид награды
     */
    void toReward(UUID profileId, Long rewardId);
    /**
     * Вернуть награду
     *
     * @param profileId внешний ид профиля игрока
     * @param rewardId  ид награды
     */
    void returnReward(UUID profileId, Long rewardId);
}
