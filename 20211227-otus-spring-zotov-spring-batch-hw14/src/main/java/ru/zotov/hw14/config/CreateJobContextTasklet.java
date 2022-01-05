package ru.zotov.hw14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.zotov.hw14.constant.MigrationTableName;
import ru.zotov.hw14.dao.MigrationRegistryRepositoryJpa;
import ru.zotov.hw14.domain.MigrationRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 04.01.2022
 * Шаг загрузки контекста джобы
 */
@Component
@RequiredArgsConstructor
public class CreateJobContextTasklet implements Tasklet {
    private final MigrationRegistryRepositoryJpa migrationRegistryRepository;

    @Nullable
    @Override
    public RepeatStatus execute(@NonNull StepContribution contribution, @NonNull ChunkContext chunkContext) throws Exception {
        ExecutionContext stepContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
        List<MigrationRegistry> migrationRegistryList = migrationRegistryRepository.findAll();

        Arrays.stream(MigrationTableName.values()).forEach(
                migrationTableName -> stepContext.put(migrationTableName.name(),
                        getRegistryMap(migrationTableName, migrationRegistryList)));

        return RepeatStatus.FINISHED;
    }

    /**
     * Получить из списка всех ключей мапу по имени таблицы
     *
     * @param migrationTableName    имя таблицы
     * @param migrationRegistryList список ид внешнего и ид энтити
     * @return мапа ид внешнего и ид энтити
     */
    private Map<String, Long> getRegistryMap(MigrationTableName migrationTableName,
            List<MigrationRegistry> migrationRegistryList) {
        return migrationRegistryList.stream()
                .filter(m -> migrationTableName.equals(m.getTableName()))
                .collect(Collectors.toMap(MigrationRegistry::getExternalId, MigrationRegistry::getEntityId));
    }
}
