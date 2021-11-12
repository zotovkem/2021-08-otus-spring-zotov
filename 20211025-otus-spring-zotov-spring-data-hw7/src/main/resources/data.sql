INSERT INTO genre (id, name)
VALUES (1, 'Детектив'),
       (2, 'Компьютерная литература'),
       (3, 'Сказки'),
       (4, 'Фантастика'),
       (5, 'Программирование'),
       (6, 'Базы данных'),
       (7, 'Архитектура ИС'),
       (8, 'Аудио книги');

INSERT INTO author (id, fio)
VALUES (1, 'Роберт Мартин'),
       (2, 'Мартин Клеппман'),
       (3, 'Билл Любанович'),
       (4, 'Александр Сергеевич Пушкин'),
       (5, 'Артем Каменистый'),
       (6, 'А. Тумаркин'),
       (7, 'Александр Киселев'),
       (8, 'Александра Маринина');

INSERT INTO book (id, name, release_year)
VALUES (1, 'Высоконагруженные приложения', 2017),
       (2, 'Чистая архитектура', 2018),
       (3, 'Правильное питание', 2021),
       (4, 'Экстремальная археология', 2021),
       (5, 'Сказки пушкина', 2008),
       (6, 'Отдаленные последствия', 2021);

INSERT INTO comment_for_book (id, book_id, content, author, create_date)
VALUES (1, 1, 'Вроде не чего, еще не дочитал', 'ЗотовЕС', '2020-02-01 19:10:25-07'),
       (2, 2, 'Хорошая книга', 'ЗотовЕС', '2020-02-01 19:10:25-07'),
       (3, 3, 'Не про гречку', 'ЗотовЕС', '2020-02-01 19:10:25-07'),
       (4, 4, 'Странное название', 'ЗотовЕС', '2020-02-01 19:10:25-07'),
       (6, 6, 'Детектива, детектива', 'ЗотовЕС', '2020-02-01 19:10:25-07'),
       (7, 1, 'Комментарий ', 'Иванов', '2020-02-01 19:10:25-07'),
       (8, 2, 'Тестовый комментарий', 'Петров', '2020-02-01 19:10:25-07'),
       (9, 3, 'Еще один комментарий', 'Сидоров', '2020-02-01 19:10:25-07'),
       (10, 4, 'Как много комментарием нужно написать', 'Тестов', '2020-02-01 19:10:25-07'),
       (12, 6, 'Последний комментарий', 'Лютый критик', '2020-02-01 19:10:25-07');


INSERT INTO mtm_book_author (id, book_id, author_id)
VALUES (1, 1, 2),
       (2, 1, 6),
       (3, 2, 1),
       (4, 2, 7),
       (5, 3, 5),
       (6, 4, 5),
       (7, 5, 4),
       (8, 6, 8);

INSERT INTO mtm_book_genre (id, book_id, genre_id)
VALUES (1, 1, 2),
       (2, 1, 5),
       (3, 2, 2),
       (4, 2, 7),
       (5, 3, 4),
       (6, 4, 4),
       (7, 5, 3),
       (8, 5, 8),
       (9, 6, 1);