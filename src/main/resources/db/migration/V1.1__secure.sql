--------------------------------------------------------------
--                  INSERT PROFILES                         --
--------------------------------------------------------------
INSERT INTO profile --passwd = 123
(id,  visibility, first_name, second_name, third_name, email, phone, password, birth_date, nationality, status, lang, created_date)
SELECT
    '1',  true, 'Ali', 'Aliyev', 'Alijonovich ', 'alijons@gmail.com', '123', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', now(), 'USA', 'ACTIVE', 'en', now()
WHERE NOT EXISTS(SELECT id
                 FROM profile
                 WHERE id = '1');


--------------------------------------------------------------
--                   INSERT PROFILE ROLES                   --
--------------------------------------------------------------
INSERT INTO profile_role
(visibility, id, created_date, profile_id, role)
SELECT
    true, '1',now(), '1', 'SUPER_ADMIN'
WHERE NOT EXISTS(SELECT
                 FROM profile_role
                 WHERE id = '1' );

INSERT INTO profile_role
(visibility, id, created_date, profile_id, role)
SELECT
    true, '2',now(), '1', 'ADMIN'
WHERE NOT EXISTS(SELECT
                 FROM profile_role
                 WHERE id = '2');

INSERT INTO profile_role
(visibility, id, created_date, profile_id, role)
SELECT
    true, '3',now(), '1', 'MIN_ADMIN'
WHERE NOT EXISTS(SELECT
                 FROM profile_role
                 WHERE id = '3');

INSERT INTO profile_role
(visibility, id, created_date, profile_id, role)
SELECT
    true, '4',now(), '1', 'STAFF'
WHERE NOT EXISTS(SELECT
                 FROM profile_role
                 WHERE id = '4');

INSERT INTO profile_role
(visibility, id, created_date, profile_id, role)
SELECT
    true, '5',now(), '1', 'USER'
WHERE NOT EXISTS(SELECT id
                 FROM profile_role
                 WHERE id = '5');