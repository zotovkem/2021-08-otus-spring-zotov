INSERT INTO race_schema.race_template(id, reward_id, name, track_length, fuel_consume, track_id, check_on_cheat)
VALUES (1, 1, 'Тестовый заезд', 100, 10, 1, true)
ON CONFLICT DO NOTHING;
COMMIT;
SELECT setval('race_schema.race_template_id_seq', 2)
