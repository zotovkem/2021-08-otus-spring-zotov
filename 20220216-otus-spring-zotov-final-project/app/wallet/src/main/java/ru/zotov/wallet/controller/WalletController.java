package ru.zotov.wallet.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.dto.WalletDto;
import ru.zotov.wallet.config.SpringfoxConfig;
import ru.zotov.wallet.service.WalletService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 03.09.2021
 * Контроллер для получения
 */
@RestController
@RequiredArgsConstructor
@Api(value = "Wallet", tags = {SpringfoxConfig.WALLET})
@RequestMapping(value = "wallet", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class WalletController {
    private final WalletService walletService;
    private final Mapper mapper;

    @GetMapping
    @ApiOperation("Получить кошелек игрока")
    public ResponseEntity<WalletDto> getPlayerWallet() {
        return walletService.getWallet()
                .map(w -> mapper.map(w, WalletDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
