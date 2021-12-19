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
       (10, 5, 'Как много комментарием нужно написать', 'Тестов', '2020-02-01 19:10:25-07'),
       (11, 6, 'Последний комментарий', 'Лютый критик', '2020-02-01 19:10:25-07');


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
INSERT INTO user_library (id, username, password, role)
VALUES (1, 'admin', '$2a$10$dX/ry6AS3VlctY/jbvu3NeKjSXKm4qjiHcjAebObrmt5CqKj743Q2', 'ROLE_ADMIN'),
       (2, 'adult', '$2a$10$dX/ry6AS3VlctY/jbvu3NeKjSXKm4qjiHcjAebObrmt5CqKj743Q2', 'ROLE_ADULT'),
       (3, 'child', '$2a$10$dX/ry6AS3VlctY/jbvu3NeKjSXKm4qjiHcjAebObrmt5CqKj743Q2', 'ROLE_CHILD');
-- ACL
INSERT INTO acl_sid (id, principal, sid)
VALUES (1, 0, 'ROLE_ADMIN'),
       (2, 0, 'ROLE_ADULT'),
       (3, 0, 'ROLE_CHILD');

INSERT INTO acl_class (id, class)
VALUES (1, 'ru.zotov.hw13.domain.Book'),
       (2, 'ru.zotov.hw13.domain.Comment');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES
    -- Книги
    (1, 1, 1, NULL, 1, 0),
    (2, 1, 2, NULL, 1, 0),
    (3, 1, 3, NULL, 1, 0),
    (4, 1, 4, NULL, 1, 0),
    (5, 1, 5, NULL, 1, 0),
    (6, 1, 6, NULL, 1, 0),
    -- Комментарии
    (7, 2, 1, NULL, 1, 0),
    (8, 2, 2, NULL, 1, 0),
    (9, 2, 3, NULL, 1, 0),
    (10, 2, 4, NULL, 1, 0),
    (11, 2, 5, NULL, 1, 0),
    (12, 2, 6, NULL, 1, 0),
    (13, 2, 7, NULL, 1, 0),
    (14, 2, 8, NULL, 1, 0),
    (15, 2, 9, NULL, 1, 0),
    (16, 2, 10, NULL, 1, 0),
    (17, 2, 11, NULL, 1, 0);

-- Книги
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES
    --Права админа
    (1, 1, 1, 1, 16, 1, 1, 1),
    (2, 2, 1, 1, 16, 1, 1, 1),
    (3, 3, 1, 1, 16, 1, 1, 1),
    (4, 4, 1, 1, 16, 1, 1, 1),
    (5, 5, 1, 1, 16, 1, 1, 1),
    (6, 6, 1, 1, 16, 1, 1, 1),
    --Права взрослого
    (7, 1, 2, 2, 1, 1, 1, 1),
    (8, 2, 2, 2, 1, 1, 1, 1),
    (9, 3, 2, 2, 1, 1, 1, 1),
    (10, 4, 2, 2, 1, 1, 1, 1),
    (11, 5, 2, 2, 1, 1, 1, 1),
    (12, 6, 2, 2, 1, 1, 1, 1),
    --Права ребенка
    (13, 1, 3, 3, 1, 0, 1, 1),
    (14, 2, 3, 3, 1, 0, 1, 1),
    (15, 3, 3, 3, 1, 0, 1, 1),
    (17, 4, 3, 3, 1, 0, 1, 1),
    (18, 5, 3, 3, 1, 1, 1, 1),
    (19, 6, 3, 3, 1, 0, 1, 1);
-- Комментарии
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES
    --Права ребенка
    (54, 16, 3, 3, 1, 1, 1, 1),
    --Права взрослого
    (55, 7, 3, 2, 2, 1, 1, 1),
    (56, 7, 4, 2, 8, 1, 1, 1);
/*public static final Permission READ = new BasePermission(1 << 0, 'R'); // 1
	public static final Permission WRITE = new BasePermission(1 << 1, 'W'); // 2
	public static final Permission CREATE = new BasePermission(1 << 2, 'C'); // 4
	public static final Permission DELETE = new BasePermission(1 << 3, 'D'); // 8
	public static final Permission ADMINISTRATION = new BasePermission(1 << 4, 'A'); // 16*/
