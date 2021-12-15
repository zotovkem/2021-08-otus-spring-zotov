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
///Security
DROP TABLE IF EXISTS user_library;
CREATE TABLE user_library
(
    id       IDENTITY PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
COMMENT ON TABLE user_library IS 'Пользователи';
COMMENT ON COLUMN user_library.id IS 'Ид';
COMMENT ON COLUMN user_library.username IS 'Имя пользователя';
COMMENT ON COLUMN user_library.password IS 'Пароль';

///ACL
DROP TABLE IF EXISTS acl_sid;
CREATE TABLE IF NOT EXISTS acl_sid
(
    id        bigint(20)   NOT NULL AUTO_INCREMENT,
    principal tinyint(1)   NOT NULL,
    sid       varchar(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_1 (sid,principal)
);

CREATE TABLE IF NOT EXISTS acl_class
(
    id    bigint(20)   NOT NULL AUTO_INCREMENT,
    class varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_2 (class)
);

CREATE TABLE IF NOT EXISTS acl_entry
(
    id                  bigint(20) NOT NULL AUTO_INCREMENT,
    acl_object_identity bigint(20) NOT NULL,
    ace_order           int(11)    NOT NULL,
    sid                 bigint(20) NOT NULL,
    mask                int(11)    NOT NULL,
    granting            tinyint(1) NOT NULL,
    audit_success       tinyint(1) NOT NULL,
    audit_failure       tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
);

CREATE TABLE IF NOT EXISTS acl_object_identity
(
    id                 bigint(20) NOT NULL AUTO_INCREMENT,
    object_id_class    bigint(20) NOT NULL,
    object_id_identity bigint(20) NOT NULL,
    parent_object      bigint(20) DEFAULT NULL,
    owner_sid          bigint(20) DEFAULT NULL,
    entries_inheriting tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
);
commit;

ALTER TABLE acl_entry
    ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (sid) REFERENCES acl_sid (id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);
