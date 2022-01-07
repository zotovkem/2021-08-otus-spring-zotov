package ru.zotov.hw14.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zotov.hw14.domain.MigrationRegistry;

/**
 * @author Created by ZotovES on 02.01.2022
 * Репозиторий перенесенных записей
 */
public interface MigrationRegistryRepository extends JpaRepository<MigrationRegistry, Long> {
}
