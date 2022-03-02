package ru.zotov.carracing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zotov.carracing.config.SpringfoxConfig;
import ru.zotov.carracing.dto.RaceFinishDto;
import ru.zotov.carracing.dto.RaceOperationDto;
import ru.zotov.carracing.repo.RaceTemplateRepo;
import ru.zotov.carracing.service.RaceService;

import javax.websocket.server.PathParam;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 17.08.2021
 * Контроллер управления зпездами
 */
@RestController
@RequiredArgsConstructor
@Api(value = "Race", tags = {SpringfoxConfig.RACE})
@RequestMapping(value = "race", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class RaceOperationController {
    private final RaceService raceService;
    private final RaceTemplateRepo raceTemplateRepo;
    private final ModelMapper mapper = new ModelMapper();

    @ApiOperation("Загрузить заезд")
    @PostMapping(value = "/{raceId}/load")
    public ResponseEntity<RaceOperationDto> raceLoad(@PathVariable("raceId") Long raceId) {
        return raceTemplateRepo.findById(raceId)
                .map(raceService::createRace)
                .map(race -> mapper.map(race, RaceOperationDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation("Стартовать заезд")
    @PostMapping("/{raceId}/start")
    public ResponseEntity<RaceOperationDto> raceStart(@PathVariable("raceId") Long raceId) {
        return Optional.of(raceService.start(raceId))
                .map(race -> mapper.map(race, RaceOperationDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation("Финиш заезда")
    @PostMapping("/finish")
    public ResponseEntity<RaceOperationDto> raceFinish(@RequestBody RaceFinishDto raceFinishDto) {
        return Optional.of(raceService.finish(raceFinishDto.getId(), raceFinishDto.getRaceExternalId()))
                .map(race -> mapper.map(race, RaceOperationDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation("Отмена заезда")
    @PostMapping("/{raceId}/cancel")
    public ResponseEntity<RaceOperationDto> raceCancel(@PathVariable("raceId") Long raceId) {
        return Optional.of(raceService.cancel(raceId))
                .map(race -> mapper.map(race, RaceOperationDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation("Получить заезд по ид")
    @GetMapping
    public ResponseEntity<RaceOperationDto> getRaceById(@PathParam("raceId") Long raceId) {
        return raceService.findById(raceId)
                .map(race -> mapper.map(race, RaceOperationDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
