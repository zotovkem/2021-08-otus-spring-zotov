package ru.zotov.hw14.utils;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.StepContext;
import ru.zotov.hw14.constant.MigrationTableName;

import java.util.HashMap;
import java.util.Map;

import static ru.zotov.hw14.constant.MigrationTableName.AUTHOR;

/**
 * @author Created by ZotovES on 05.01.2022
 * Утилитарный класс для работы с контекстом джобы
 */
public class BatchJobContextHelper {
    /**
     * Получить мапу по имени таблицы из контекста джобы
     * @param stepExecution контекст шага
     * @param migrationTableName имя таблицы
     * @return мапа внешнего ид и ид энтити
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Long> getContextMapByTableNameEnum(StepExecution stepExecution, MigrationTableName migrationTableName) {
        Object map = stepExecution.getJobExecution().getExecutionContext().get(migrationTableName.name());
        if (map instanceof Map) {
            return ((Map<String, Long>) map);
        }
        return new HashMap<String, Long>();
    }
}
