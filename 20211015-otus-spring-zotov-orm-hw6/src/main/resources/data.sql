INSERT INTO genre (id, name)
values (1, 'Детектив');
INSERT INTO genre (id, name)
values (2, 'Компьютерная литература');
INSERT INTO genre (id, name)
values (3, 'Сказки');
INSERT INTO genre (id, name)
values (4, 'Фантастика');
INSERT INTO genre (id, name)
values (5, 'Программирование');
INSERT INTO genre (id, name)
values (6, 'Базы данных');
INSERT INTO genre (id, name)
values (7, 'Архитектура ИС');
INSERT INTO genre (id, name)
values (8, 'Аудио книги');

INSERT INTO author (id, fio)
values (1, 'Роберт Мартин');
INSERT INTO author (id, fio)
values (2, 'Мартин Клеппман');
INSERT INTO author (id, fio)
values (3, 'Билл Любанович');
INSERT INTO author (id, fio)
VALUES (4, 'Александр Сергеевич Пушкин');
INSERT INTO author (id, fio)
VALUES (5, 'Артем Каменистый');
INSERT INTO author (id, fio)
VALUES (6, 'А. Тумаркин');
INSERT INTO author (id, fio)
VALUES (7, 'Александр Киселев');
INSERT INTO author (id, fio)
VALUES (8, 'Александра Маринина');

INSERT INTO book (id, name, release_year)
VALUES (1, 'Высоконагруженные приложения', 2017);
INSERT INTO book (id, name, release_year)
VALUES (2, 'Чистая архитектура', 2018);
INSERT INTO book (id, name, release_year)
VALUES (3, 'Правильное питание', 2021);
INSERT INTO book (id, name, release_year)
VALUES (4, 'Экстремальная археология', 2021);
INSERT INTO book (id, name, release_year)
VALUES (5, 'Сказки пушкина', 2008);
INSERT INTO book (id, name, release_year)
VALUES (6, 'Отдаленные последствия', 2021);

INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (1, 1, 'Вроде не чего, еще не дочитал', 'ЗотовЕС', '2020-02-01 19:10:25-07');
INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (2, 2, 'Хорошая книга', 'ЗотовЕС', '2020-02-01 19:10:25-07');
INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (3, 3, 'Не про гречку', 'ЗотовЕС', '2020-02-01 19:10:25-07');
INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (4, 4, 'Странное название', 'ЗотовЕС', '2020-02-01 19:10:25-07');
INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (6, 6, 'Детектива, детектива', 'ЗотовЕС', '2020-02-01 19:10:25-07');
INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (7, 1, 'Комментарий ', 'Иванов', '2020-02-01 19:10:25-07');
INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (8, 2, 'Тестовый комментарий', 'Петров', '2020-02-01 19:10:25-07');
INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (9, 3, 'Еще один комментарий', 'Сидоров', '2020-02-01 19:10:25-07');
INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (10, 4, 'Как много комментарием нужно написать', 'Тестов', '2020-02-01 19:10:25-07');
INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (12, 6, 'Последний комментарий', 'Лютый критик', '2020-02-01 19:10:25-07');


INSERT INTO mtm_book_author (id, book_id, author_id)
VALUES (1, 1, 2);
INSERT INTO mtm_book_author (id, book_id, author_id)
VALUES (2, 1, 6);
INSERT INTO mtm_book_author (id, book_id, author_id)
VALUES (3, 2, 1);
INSERT INTO mtm_book_author (id, book_id, author_id)
VALUES (4, 2, 7);
INSERT INTO mtm_book_author (id, book_id, author_id)
VALUES (5, 3, 5);
INSERT INTO mtm_book_author (id, book_id, author_id)
VALUES (6, 4, 5);
INSERT INTO mtm_book_author (id, book_id, author_id)
VALUES (7, 5, 4);
INSERT INTO mtm_book_author (id, book_id, author_id)
VALUES (8, 6, 8);

INSERT INTO mtm_book_genre (id, book_id, genre_id)
VALUES (1, 1, 2);
INSERT INTO mtm_book_genre (id, book_id, genre_id)
VALUES (2, 1, 5);
INSERT INTO mtm_book_genre (id, book_id, genre_id)
VALUES (3, 2, 2);
INSERT INTO mtm_book_genre (id, book_id, genre_id)
VALUES (4, 2, 7);
INSERT INTO mtm_book_genre (id, book_id, genre_id)
VALUES (5, 3, 4);
INSERT INTO mtm_book_genre (id, book_id, genre_id)
VALUES (6, 4, 4);
INSERT INTO mtm_book_genre (id, book_id, genre_id)
VALUES (7, 5, 3);
INSERT INTO mtm_book_genre (id, book_id, genre_id)
VALUES (8, 5, 8);
INSERT INTO mtm_book_genre (id, book_id, genre_id)
VALUES (9, 6, 1);
