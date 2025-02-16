package ru.msnigirev.oris.collaboration.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.msnigirev.oris.collaboration.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("user_id"))
                .username(resultSet.getString("username"))
                .publicName(resultSet.getString("public_name"))
                .password(resultSet.getString("password"))
                .email(resultSet.getString("email"))
                .phone(resultSet.getString("phone"))
                .avatarUrl(resultSet.getString("avatar_url"))
                .build();
    }

}
