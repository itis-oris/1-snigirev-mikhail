package ru.msnigirev.oris.collaboration.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {
    private int id;
    private String username;
    private String publicName;
    private String password;
    private String email;
    private String phone;
    private String avatarUrl;
    /*
    CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       public_name VARCHAR(50) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       phone VARCHAR(15) NOT NULL UNIQUE,
                       avatar_url VARCHAR(255),
                       description TEXT
    );
     */
}
