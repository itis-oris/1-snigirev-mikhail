-- Вставка пользователей
INSERT INTO users (username, public_name, password, email, phone, avatar_url) VALUES
           ('misha', 'misha', '$2a$10$IbWs8mLgKTuia45EzCfc8eKaaKlbkNZj6s9rffajB/3q.OJAZUxDO', 'geyshrk@mail.ru', '1234567890', 'http://example.com/avatars/john.jpg');

-- Вставка учителей
INSERT INTO teachers (name) VALUES
                                ('Enikeev'),
                                ('Abramsky');

-- Вставка предметов
INSERT INTO subjects (name) VALUES
                                ('inf'),
                                ('bd');

-- Вставка институтов
INSERT INTO institutes (name) VALUES
                 ('ITIS');

-- Вставка проектов
INSERT INTO projects (name, description, creator_id, subject_id, institute_id, teacher_id, year) VALUES
    ('project', 'myproject', 1, 9, 8, 8, 2024, '/projects/folders/ae0cd36f-c0ed-4c88-aa79-84d7673f0965_project', '/projects/avatars/108414ce-5110-4eb3-b325-7df951100657_project');
-- Вставка администраторов проектов
INSERT INTO project_admins (project_id, user_id) VALUES
                                                     (1, 1);
