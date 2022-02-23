INSERT INTO profile_schema.profile(id, external_id, progress, current_car_id, score, level)
VALUES (1,'40d1e5f9-0dcb-44db-8de7-86104d8b3928',1,1,100,2) ON CONFLICT DO NOTHING;
commit;
SELECT setval('profile_schema.profile_id_seq', 2)
