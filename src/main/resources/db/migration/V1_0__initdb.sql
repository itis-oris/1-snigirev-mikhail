CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       public_name VARCHAR(50) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       phone VARCHAR(15) NOT NULL UNIQUE,
                       avatar_url VARCHAR(255),
                       csrf_token varchar(255)
);
CREATE TABLE teachers (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

CREATE TABLE subjects (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

CREATE TABLE institutes (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);
CREATE TABLE projects (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description TEXT,
                          creator_id INT,
                          subject_id INT,
                          institute_id INT,
                          teacher_id INT,
                          year INT,
                          folder varchar(255),
                          avatar_url varchar(255),
                          FOREIGN KEY (creator_id) REFERENCES users(user_id) ON DELETE CASCADE,
                          FOREIGN KEY (subject_id) REFERENCES subjects(id),
                          FOREIGN KEY (teacher_id) REFERENCES teachers(id),
                          FOREIGN KEY (institute_id) REFERENCES institutes(id)
);
CREATE TABLE project_admins (
                                project_id INT,
                                user_id INT,
                                PRIMARY KEY (project_id, user_id),
                                FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                                FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);


