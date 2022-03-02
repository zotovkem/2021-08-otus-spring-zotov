package ru.zotov.carracing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zotov.carracing.enums.RaceState;

import java.util.UUID;

/**
 * @author Created by ZotovES on 17.08.2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RaceOperationDto: Статус заезда ")
public class RaceOperationDto {
    @ApiModelProperty(value = "Ид", example = "1")
    private Long id;
    @ApiModelProperty(value = "Внешний ид", example = "40d1e5f9-0dcb-44db-8de7-86104d8b3928")
    private UUID externalId;
    @ApiModelProperty(value = "Состояние заезда", example = "LOAD",
                      allowableValues = "LOAD,LOAD_FAILED,LOADED,START,FINISH_SUCCESS,FINISH_FAILED,CANCEL")
    private RaceState state;
}
