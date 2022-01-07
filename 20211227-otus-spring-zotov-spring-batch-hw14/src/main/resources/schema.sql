DROP TABLE IF EXISTS author;
CREATE TABLE author
(
    id  IDENTITY PRIMARY KEY,
    fio VARCHAR(255)
);
COMMENT ON TABLE author IS 'Авторы';
COMMENT ON COLUMN author.id IS 'Ид';
COMMENT ON COLUMN author.fio IS 'ФИО автора';

DROP TABLE IF EXISTS genre;
CREATE TABLE genre
(
    id   IDENTITY PRIMARY KEY,
    name VARCHAR(255)
);
COMMENT ON TABLE genre IS 'Жанры';
COMMENT ON COLUMN genre.id IS 'Ид';
COMMENT ON COLUMN genre.name IS 'Наименование';

DROP TABLE IF EXISTS book;
CREATE TABLE book
(
    id           IDENTITY PRIMARY KEY,
    name         VARCHAR(255),
    release_year INT
);
COMMENT ON TABLE book IS 'Книги';
COMMENT ON COLUMN book.id IS 'Ид';
COMMENT ON COLUMN book.name IS 'Наименование';
COMMENT ON COLUMN book.release_year IS 'Год издания';

DROP TABLE IF EXISTS mtm_book_author;
CREATE TABLE mtm_book_author
(
    id        BIGINT AUTO_INCREMENT NOT NULL UNIQUE,
    book_id   BIGINT                NOT NULL
        CONSTRAINT book_mtm_book_author_fk REFERENCES book ON DELETE CASCADE,
    author_id BIGINT                NOT NULL
        CONSTRAINT book_author_fk REFERENCES author ON DELETE RESTRICT
);
COMMENT ON TABLE mtm_book_author IS 'Таблица связи книг с авторами';
COMMENT ON COLUMN mtm_book_author.id IS 'Ид';
COMMENT ON COLUMN mtm_book_author.book_id IS 'Ид книги';
COMMENT ON COLUMN mtm_book_author.author_id IS 'Ид автора';
CREATE INDEX mtm_book_author_book_id_idx ON mtm_book_author (book_id);

DROP TABLE IF EXISTS mtm_book_genre;
CREATE TABLE mtm_book_genre
(
    id       BIGINT AUTO_INCREMENT NOT NULL UNIQUE,
    book_id  BIGINT                NOT NULL
        CONSTRAINT book_mtm_book_genre_fk REFERENCES book ON DELETE CASCADE,
    genre_id BIGINT                NOT NULL
        CONSTRAINT book_genre_fk REFERENCES genre ON DELETE RESTRICT
);
COMMENT ON TABLE mtm_book_genre IS 'Таблица связи книг с жанрами';
COMMENT ON COLUMN mtm_book_genre.id IS 'Ид';
COMMENT ON COLUMN mtm_book_genre.book_id IS 'Ид книги';
COMMENT ON COLUMN mtm_book_genre.genre_id IS 'Ид жанра';
CREATE INDEX mtm_genre_author_book_id_idx ON mtm_book_genre (book_id);

DROP TABLE IF EXISTS comment_for_book;
CREATE TABLE comment_for_book
(
    id          IDENTITY PRIMARY KEY,
    book_id     BIGINT                   NOT NULL
        CONSTRAINT book_comment_for_book_fk REFERENCES book ON DELETE CASCADE,
    content     VARCHAR(255),
    author      VARCHAR(255),
    create_date TIMESTAMP WITH TIME ZONE NOT NULL
);
COMMENT ON TABLE comment_for_book IS 'Комментарии к книге';
COMMENT ON COLUMN comment_for_book.id IS 'Ид';
COMMENT ON COLUMN comment_for_book.book_id IS 'Ид книги';
COMMENT ON COLUMN comment_for_book.content IS 'Текст комментария';
COMMENT ON COLUMN comment_for_book.author IS 'Автор комментария';
COMMENT ON COLUMN comment_for_book.create_date IS 'Дата комментария';

DROP TABLE IF EXISTS migration_registry;
CREATE TABLE migration_registry
(
    id          IDENTITY PRIMARY KEY,
    entity_id     BIGINT                   NOT NULL,
    external_id     VARCHAR(255),
    table_name      VARCHAR(255)
);
COMMENT ON TABLE migration_registry IS 'Реестр перенесенных записей';
COMMENT ON COLUMN migration_registry.id IS 'Ид';
COMMENT ON COLUMN migration_registry.entity_id IS 'Ид записи в целевой БД';
COMMENT ON COLUMN migration_registry.external_id IS 'Ид во внешней БД';
COMMENT ON COLUMN migration_registry.table_name IS 'Имя таблицы';
