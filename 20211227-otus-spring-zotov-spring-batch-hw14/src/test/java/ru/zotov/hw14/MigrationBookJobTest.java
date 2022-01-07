package ru.zotov.hw14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.zotov.hw14.dao.AuthorRepository;
import ru.zotov.hw14.dao.BookRepository;
import ru.zotov.hw14.dao.CommentRepository;
import ru.zotov.hw14.dao.GenreRepository;
import ru.zotov.hw14.domain.jpa.AuthorJpa;
import ru.zotov.hw14.domain.jpa.BookJpa;
import ru.zotov.hw14.domain.jpa.CommentJpa;
import ru.zotov.hw14.domain.jpa.GenreJpa;
import ru.zotov.hw14.domain.mongo.AuthorMongo;
import ru.zotov.hw14.domain.mongo.BookMongo;
import ru.zotov.hw14.domain.mongo.CommentMongo;
import ru.zotov.hw14.domain.mongo.GenreMongo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.zotov.hw14.constant.Constants.JOB_NAME;

/**
 * @author Created by ZotovES on 06.01.2022
 */
@SpringBootTest
@SpringBatchTest
@DisplayName("Тестирование миграции данных о книгах из Mongo в H2")
public class MigrationBookJobTest {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private JobRepositoryTestUtils jobRepositoryTestUtils;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(JOB_NAME);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParameters());

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        List<AuthorJpa> authorJpaList = authorRepository.findAll();
        List<AuthorMongo> authorMongoList = mongoTemplate.findAll(AuthorMongo.class);

        assertThat(authorJpaList.size()).isEqualTo(authorMongoList.size());

        List<GenreJpa> genreJpaList = genreRepository.findAll();
        List<GenreMongo> genreMongoList = mongoTemplate.findAll(GenreMongo.class);

        assertThat(genreJpaList.size()).isEqualTo(genreMongoList.size());

        List<BookJpa> booksJpa = bookRepository.findAll();
        List<BookMongo> booksMongo = mongoTemplate.findAll(BookMongo.class);

        assertThat(booksJpa.size()).isEqualTo(booksMongo.size());

        List<CommentJpa> commentJpaList = commentRepository.findAll();
        List<CommentMongo> commentMongoList = booksMongo.stream()
                .map(BookMongo::getComments)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        assertThat(commentJpaList.size()).isEqualTo(commentMongoList.size());
    }
}
