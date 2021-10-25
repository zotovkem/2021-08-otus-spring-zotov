INSERT INTO genre (id, name)
VALUES (1, 'Детектив'),
       (2, 'Компьютерная литература');

INSERT INTO author (id, fio)
VALUES (1, 'Роберт Мартин'),
       (2, 'Александр Сергеевич Пушкин');

INSERT INTO book (id, name, release_year)
VALUES (1, 'Высоконагруженные приложения', 2017),
       (2, 'Чистая архитектура', 2018);

INSERT INTO mtm_book_author (id, book_id, author_id)
VALUES (1, 1, 1),
       (2, 2, 2);

INSERT INTO mtm_book_genre (id, book_id, genre_id)
VALUES (1, 1, 1),
       (2, 2, 2);

INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (1, 1, 'Вроде не чего, еще не дочитал', 'ЗотовЕС', '2020-02-01 19:10:25-07'),
       (2, 2, 'Хорошая книга', 'ЗотовЕС', '2020-02-01 19:10:25-07'),
       (3, 1, 'Комментарий', 'Иванов', '2020-02-01 19:10:25-07'),
       (4, 2, 'Тестовый комментарий', 'Петров', '2020-02-01 19:10:25-07');
