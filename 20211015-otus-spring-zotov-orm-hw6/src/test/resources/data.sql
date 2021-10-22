INSERT INTO genre (id, name)
values (1, 'Детектив');
INSERT INTO genre (id, name)
values (2, 'Компьютерная литература');

INSERT INTO author (id, fio)
values (1, 'Роберт Мартин');
INSERT INTO author (id, fio)
VALUES (2, 'Александр Сергеевич Пушкин');

INSERT INTO book (id, name, release_year)
VALUES (1, 'Высоконагруженные приложения', 2017);
INSERT INTO book (id, name, release_year)
VALUES (2, 'Чистая архитектура', 2018);

INSERT INTO mtm_book_author (id, book_id, author_id)
VALUES (1, 1, 1);
INSERT INTO mtm_book_author (id, book_id, author_id)
VALUES (2, 2, 2);

INSERT INTO mtm_book_genre (id, book_id, genre_id)
VALUES (1, 1, 1);
INSERT INTO mtm_book_genre (id, book_id, genre_id)
VALUES (2, 2, 2);

INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (1, 1, 'Вроде не чего, еще не дочитал', 'ЗотовЕС', '2020-02-01 19:10:25-07');
INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (2, 2, 'Хорошая книга', 'ЗотовЕС', '2020-02-01 19:10:25-07');
INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (7, 1, 'Комментарий ', 'Иванов', '2020-02-01 19:10:25-07');
INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (8, 2, 'Тестовый комментарий', 'Петров', '2020-02-01 19:10:25-07');
