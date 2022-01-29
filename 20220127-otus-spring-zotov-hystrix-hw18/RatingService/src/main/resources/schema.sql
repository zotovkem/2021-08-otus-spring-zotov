DROP TABLE IF EXISTS rating_book;
CREATE TABLE rating_book
(
    id   IDENTITY PRIMARY KEY,
    book_id BIGINT,
    rating BIGINT
);
COMMENT ON TABLE rating_book IS 'Рейтинг книг';
COMMENT ON COLUMN rating_book.id IS 'Ид';
COMMENT ON COLUMN rating_book.book_id IS 'Ид книг';
COMMENT ON COLUMN rating_book.rating IS 'Рейтинг';
