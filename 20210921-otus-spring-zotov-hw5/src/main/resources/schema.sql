DROP TABLE IF EXISTS author;
CREATE TABLE author
(
    id  BIGINT PRIMARY KEY,
    fio VARCHAR(255)
);

DROP TABLE IF EXISTS genre;
CREATE TABLE genre
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

DROP TABLE IF EXISTS book;
CREATE TABLE book
(
    id           BIGINT PRIMARY KEY,
    name         VARCHAR(255),
    release_year INT
);
