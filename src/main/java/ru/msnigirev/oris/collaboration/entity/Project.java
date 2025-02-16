package ru.msnigirev.oris.collaboration.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class Project {
    private int id;
    private String name;
    private String description;
    private int creatorId;
    private int teacherId;
    private int instituteId;
    private int subjectId;
    private int year;
    private String folder;
    String avatar;
    private Set<User> admins;
    /*
    CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       public_name VARCHAR(50) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       phone VARCHAR(15) NOT NULL UNIQUE,
                       avatar_url VARCHAR(255),
                       csrf_token uuid,
                       description TEXT
    );
     */
}
