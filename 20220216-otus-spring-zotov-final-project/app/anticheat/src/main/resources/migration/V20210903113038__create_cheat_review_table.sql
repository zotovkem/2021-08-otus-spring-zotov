CREATE TABLE anticheat_schema.race_result
(
    id               BIGSERIAL NOT NULL PRIMARY KEY UNIQUE,
    profile_id       UUID      NOT NULL,
    external_race_id UUID      NOT NULL,
    race_start_time  BIGINT    NOT NULL,
    race_finish_time BIGINT,
    reward_id        BIGINT    NOT NULL,
    race_valid       BOOLEAN   NOT NULL DEFAULT TRUE,
    description      VARCHAR(255)
);

COMMENT ON TABLE anticheat_schema.race_result IS 'Результат проверки заезда на читерство';
COMMENT ON COLUMN anticheat_schema.race_result.id IS 'Уникальный идентификатор';
COMMENT ON COLUMN anticheat_schema.race_result.profile_id IS 'Ид профиля игрока';
COMMENT ON COLUMN anticheat_schema.race_result.external_race_id IS 'Ид заезда';
COMMENT ON COLUMN anticheat_schema.race_result.race_start_time IS 'Время старта заезда';
COMMENT ON COLUMN anticheat_schema.race_result.race_finish_time IS 'Время финиша заезда';
COMMENT ON COLUMN anticheat_schema.race_result.reward_id IS 'Ид награды';
COMMENT ON COLUMN anticheat_schema.race_result.race_valid IS 'Признак успешной проверки на читерство';
COMMENT ON COLUMN anticheat_schema.race_result.description IS 'Причина не успешной проверки';

CREATE INDEX cheat_review_external_race_id_idx ON anticheat_schema.race_result (external_race_id);
