package ru.zotov.hw14.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

/**
 * @author Created by ZotovES on 08.10.2021
 * Контроллер команд управления миграцией
 */
@SuppressWarnings("unused")
@ShellComponent
@RequiredArgsConstructor
public class BatchShellCommandController {

    /**
     * Рестарт задания миграции
     *
     */
    @ShellMethod(value = "Restart migration", key = {"restart"})
    public void restartMigration() {

    }
}
