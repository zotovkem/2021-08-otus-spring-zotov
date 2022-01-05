package ru.zotov.hw14.utils;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import ru.zotov.hw14.constant.MigrationTableName;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by ZotovES on 05.01.2022
 * Утилитарный класс для работы с контекстом джобы
 */
public class BatchJobContextHelper {
    /**
     * Получить мапу по имени таблицы из контекста джобы
     *
     * @param stepExecution      контекст шага
     * @param migrationTableName имя таблицы
     * @return мапа внешнего ид и ид энтити
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Long> getContextMapByTableNameEnum(StepExecution stepExecution,
            MigrationTableName migrationTableName) {
        Object map = stepExecution.getJobExecution().getExecutionContext().get(migrationTableName.name());
        if (map instanceof Map) {
            return ((Map<String, Long>) map);
        }
        return new HashMap<>();
    }

    /**
     * Сохраняет в контекст шага информацию об импортированных записях
     *
     * @param addedRow      сохраненные записи
     * @param stepExecution контекст шага
     * @param tableNameEnum энум имени таблицы
     */
    public static void saveStepExecutionContext(Map<String, Long> addedRow, StepExecution stepExecution,
            MigrationTableName tableNameEnum) {
        ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();
        Map<String, Long> contextMap = getContextMapByTableNameEnum(stepExecution, tableNameEnum);
        contextMap.putAll(addedRow);

        stepExecutionContext.put(tableNameEnum.name(), contextMap);
    }
}
