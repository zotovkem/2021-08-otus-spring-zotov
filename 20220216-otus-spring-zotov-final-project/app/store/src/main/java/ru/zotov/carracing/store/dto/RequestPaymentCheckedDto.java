package ru.zotov.carracing.store.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 11.09.2021
 * Входящяя дто для проверки покупки
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RequestPaymentCheckedDto: Запрос проверки покупки ")
public class RequestPaymentCheckedDto {
    @ApiModelProperty(value = "Ид", example = "1")
    private String id;
    @ApiModelProperty(value = "Токен покупки", example = "dsgrhvdbkjhfgvsdbkjfghvd72345689374tgfhbwq")
    private String token;
}
