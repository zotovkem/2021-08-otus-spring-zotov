package ru.zotov.carracing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Created by ZotovES on 27.04.2021
 * Dto авто
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "CarDto: Авто ")
public class CarDto {
    @ApiModelProperty(value = "Ид", example = "1")
    private Long id;
    @ApiModelProperty(value = "Внешний ид", example = "de8c2ad1-9562-4c3c-a89c-0979fcee338b")
    private UUID carId;
    @ApiModelProperty(value = "Наименование авто", example = "Kia rio")
    private String name;
    @ApiModelProperty(value = "Мощность", example = "144")
    private Integer power;
    @ApiModelProperty(value = "Максимальная скорость", example = "100")
    private Integer maxSpeed;
}
