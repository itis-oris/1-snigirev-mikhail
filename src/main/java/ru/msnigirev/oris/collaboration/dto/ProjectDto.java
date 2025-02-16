package ru.msnigirev.oris.collaboration.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.msnigirev.oris.collaboration.entity.User;

import java.util.Set;

@Getter
@Setter
@Builder
public class ProjectDto {
    private int id;
    private String name;
    private String description;
    private int creatorId;
    private String teacher;
    private String institute;
    private String subject;
    private int year;
    String avatar;
    String folder;
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
