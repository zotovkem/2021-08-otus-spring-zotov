insert into auth_schema.users(id,profile_id,email, nickname,password)
values (1,'ace942a1-220c-4b25-9fda-bf580f2bfc8f','test@mail.ru', 'test','$2a$10$JEcf5SUsqnWWlZK5i2iuIeNJQBsYLHlpV6eqlW3RRmh9kO6YoNr2a')
ON CONFLICT DO NOTHING;
commit;
SELECT setval('auth_schema.users_id_seq', 2)
