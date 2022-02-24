insert into race_schema.race(id, external_Id, profile_Id, race_template_id, state, race_start_time)
VALUES (1, 'b84085ff-9945-4c0c-a2a1-1001b237de0d', '62e9a377-6078-488f-bb4c-3de998293086', 1, 'LOAD', '1645708597000')
ON CONFLICT DO NOTHING;
COMMIT;
insert into race_schema.race(id, external_Id, profile_Id, race_template_id, state, race_start_time)
VALUES (2, 'a519ad91-b2ff-4e98-9cc0-31eba8dfedc2', 'a519ad91-b2ff-4e98-9cc0-31eba8dfedc2', 1, 'LOAD', '1645708597000')
ON CONFLICT DO NOTHING;
COMMIT;
SELECT setval('race_schema.race_id_seq', 3)
