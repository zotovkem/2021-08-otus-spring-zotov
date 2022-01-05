package ru.zotov.hw14.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * @author Created by ZotovES on 05.01.2022
 * Контроллер команд управления миграцией
 */
@SuppressWarnings("unused")
@ShellComponent
@RequiredArgsConstructor
public class BatchShellCommandController {
    private final Job migrationBookJob;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    /**
     * Старт задания миграции
     */
    @ShellMethod(value = "startMigration", key = "start")
    public void startMigrationJobWithJobLauncher() throws Exception {
        jobLauncher.run(migrationBookJob,
                new JobParametersBuilder(jobExplorer).getNextJobParameters(migrationBookJob).toJobParameters());
    }
}
