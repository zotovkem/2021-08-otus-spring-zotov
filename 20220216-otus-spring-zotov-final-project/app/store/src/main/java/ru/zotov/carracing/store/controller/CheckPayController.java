package ru.zotov.carracing.store.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.store.config.SpringfoxConfig;
import ru.zotov.carracing.store.dto.RequestPaymentCheckedDto;
import ru.zotov.carracing.store.dto.ResponsePaymentCheckedDto;
import ru.zotov.carracing.store.entity.PurchaseTransaction;
import ru.zotov.carracing.store.service.PurchaseTransactionService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 11.09.2021
 * Контроллер для совершения покупок
 */
@RestController
@RequiredArgsConstructor
@Api(value = "Purchase", tags = {SpringfoxConfig.STORE})
@RequestMapping(value = "purchase", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class CheckPayController {
    private final Mapper mapper;
    private final PurchaseTransactionService purchaseTransactionService;

    @PostMapping
    @ApiOperation("Создать покупку")
    public ResponseEntity<ResponsePaymentCheckedDto> createPurchase(@RequestBody RequestPaymentCheckedDto paymentCheckedDto) {
        purchaseTransactionService.createPurchaseTransaction(paymentCheckedDto.getId(), paymentCheckedDto.getToken());

        ResponsePaymentCheckedDto responsePaymentCheckedDto = ResponsePaymentCheckedDto.builder()
                .id(paymentCheckedDto.getId())
                .token(paymentCheckedDto.getToken())
                .build();

        return ResponseEntity.ok(responsePaymentCheckedDto);
    }

    @GetMapping("/notChecked")
    @ApiOperation("Получить все покупки в ожидании проверки ")
    public ResponseEntity<List<ResponsePaymentCheckedDto>> getAllNotCheckedPurchaseTransactions() {
        List<ResponsePaymentCheckedDto> purchaseTransactionList =
                mapper.mapList(purchaseTransactionService.findAllNotCheckedPurchaseTransactions(),
                        ResponsePaymentCheckedDto.class);

        return ResponseEntity.ok(purchaseTransactionList);
    }
}
