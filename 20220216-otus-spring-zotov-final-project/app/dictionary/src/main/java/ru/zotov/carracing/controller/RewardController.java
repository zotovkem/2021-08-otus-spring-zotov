package ru.zotov.carracing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.config.SpringfoxConfig;
import ru.zotov.carracing.dto.RewardDto;
import ru.zotov.carracing.entity.Reward;
import ru.zotov.carracing.repo.RewardRepo;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 10.08.2021
 * Контроллер наград
 */
@RestController
@RequiredArgsConstructor
@Api(value = "Reward", tags = {SpringfoxConfig.REWARD})
@RequestMapping(value = "rewards", produces = APPLICATION_JSON_VALUE)
public class RewardController {
    private final RewardRepo rewardRepo;
    private final Mapper mapper;

    @ApiOperation("Создать награду")
    @PostMapping
    public ResponseEntity<RewardDto> createReward(@RequestBody RewardDto reward) {
        return Optional.ofNullable(reward)
                .map(r -> mapper.map(r, Reward.class))
                .map(rewardRepo::save)
                .map(r -> mapper.map(r, RewardDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @ApiOperation("Получить награду по ид")
    @GetMapping("{rewardId}")
    public ResponseEntity<RewardDto> getById(@PathVariable("rewardId") Long rewardId) {
        return rewardRepo.findById(rewardId)
                .map(r -> mapper.map(r, RewardDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @ApiOperation(" Получить все награды")
    @GetMapping
    public ResponseEntity<List<RewardDto>> getAllRewards() {
        return ResponseEntity.ok(mapper.mapList(rewardRepo.findAll(),RewardDto.class));
    }

    @ApiOperation("Редактировать награду по ид")
    @PutMapping("{rewardId}")
    public ResponseEntity<RewardDto> updateReward(@PathVariable("rewardId") Long rewardId, @RequestBody RewardDto rewardDto) {
        return rewardRepo.findById(rewardId)
                .map(r -> mapper.map(r, Reward.class))
                .map(updateFieldReward(rewardDto))
                .map(rewardRepo::save)
                .map(r -> mapper.map(r, RewardDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @ApiOperation("Удалить награду по ид")
    @DeleteMapping("{rewardId}")
    public ResponseEntity<Void> deleteById(@PathVariable("rewardId") Long rewardId) {
        rewardRepo.deleteById(rewardId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Обновление полей награды
     *
     * @param incomeReward дто награды
     */
    private Function<Reward, Reward> updateFieldReward(RewardDto incomeReward) {
        return reward -> {
            reward.setName(incomeReward.getName());
            reward.setRewardType(incomeReward.getRewardType());
            reward.setTotal(incomeReward.getTotal());

            return reward;
        };
    }
}
