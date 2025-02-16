package ru.msnigirev.oris.collaboration.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.msnigirev.oris.collaboration.entity.User;
import ru.msnigirev.oris.collaboration.repository.interfaces.UserRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> rowMapper;

    private final static String ADD_AVATAR = "UPDATE users SET avatar_url = ? WHERE username = ?";

    private final static String GET_USERNAME_BY_ID = "select username from users where user_id = ?";
    private final static String GET_ID_BY_USERNAME = "select user_id from users where username = ?";

    private final static String GET_BY_ID = "select * from users where user_id = ?";
    private final static String CREATE = "INSERT INTO users " +
            "(username, public_name, password, email, phone, avatar_url, description) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";
    private final static String GET_AVATAR = "SELECT avatar_url FROM users WHERE username = ?";
    private final static String GET_BY_USERNAME = "select * from users where username = ?";
    private final static String GET_USERNAME_BY_TOKEN = "select username from users where csrf_token = ?";
    private final static String GET_ALL = "select * from users";
    private final static String GET_ALL_PAGEABLE = "select * from users offset ? limit ?";
    private final static String DELETE_CSRF_TOKEN = "UPDATE users SET csrf_token = NULL WHERE csrf_token = ?";
    private final static String ADD_CSRF_TOKEN = "UPDATE users SET csrf_token = ? WHERE username = ?";
    private final static String GET_BY_CSRF_TOKEN = "select * from users where csrf_token = ?";
    private final static String REGISTER_NEW_USER = "INSERT INTO users (username, public_name, email, phone, password) " +
            "VALUES (?, ?, ?, ?, ?);";
    private final static String UPDATE_USER = "UPDATE users SET " +
            "username = ?, " +
            "public_name = ?, " +
            "password = ?, " +
            "email = ?, " +
            "phone = ?, " +
            "avatar_url = ?, " +
            "description = ? " +
            "WHERE user_id = ?";

    private final static String DELETE_USER = "DELETE FROM users WHERE user_id = ?";

    public UserRepositoryImpl(DataSource dataSource, RowMapper<User> rowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.rowMapper = rowMapper;
    }

    @Override
    public void create(User user) {
        jdbcTemplate.update(CREATE, user.getUsername(),
                user.getPublicName(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.getAvatarUrl(),
                user.getId());
    }

    @Override
    public Optional<User> getById(Integer id) {
        List<User> users = jdbcTemplate.query(GET_BY_ID, rowMapper, id);
        return optionalSingleResult(users);
    }

    private Optional<User> optionalSingleResult(List<User> users) {
        if (users.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1);
        } else {
            return users.stream().findAny();
        }
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(GET_ALL, rowMapper);
    }

    @Override
    public List<User> getAll(int offset, int size) {
        return jdbcTemplate.query(GET_ALL_PAGEABLE, rowMapper, offset, size);
    }

    @Override
    public boolean update(User user) {
        int rowsAffected = jdbcTemplate.update(UPDATE_USER,
                user.getUsername(),
                user.getPublicName(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.getAvatarUrl(),
                user.getId());
        return rowsAffected > 0;
    }

    @Override
    public boolean delete(User user) {
        int rowsAffected = jdbcTemplate.update(DELETE_USER, user.getId());
        return rowsAffected > 0;
    }

    @Override
    public String getAvatarPath(String username){
        try {
            return jdbcTemplate.queryForObject(GET_AVATAR, String.class, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }



    @Override
    public String getUsernameByToken(String csrfToken) {
        try {
            return jdbcTemplate.queryForObject(GET_USERNAME_BY_TOKEN, String.class, csrfToken);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteCsrfToken(String csrfToken) {
        jdbcTemplate.update(DELETE_CSRF_TOKEN, csrfToken);
    }

    @Override
    public void addCsrfToken(String csrfToken, String username) {
        jdbcTemplate.update(ADD_CSRF_TOKEN, csrfToken, username);
    }

    @Override
    public boolean csrfTokenExists(String csrfToken) {
        List<User> users = jdbcTemplate.query(GET_BY_CSRF_TOKEN, rowMapper, csrfToken);
        return optionalSingleResult(users).isPresent();
    }

    @Override
    public void registerNewUser(String username, String publicName, String email, String phoneNumber, String password) {
        jdbcTemplate.update(REGISTER_NEW_USER, username, publicName, email, phoneNumber, password);
    }

    @Override
    public void addAvatar(String url, String username) {
        jdbcTemplate.update(ADD_AVATAR, url, username);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        List<User> users = jdbcTemplate.query(GET_BY_USERNAME, rowMapper, username);
        return optionalSingleResult(users);
    }

    @Override
    public String getUsernameById(int id) {
        try {
            return jdbcTemplate.queryForObject(GET_USERNAME_BY_ID, String.class, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }
    @Override
    public int getIdByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject(GET_ID_BY_USERNAME, Integer.class, username);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }

    }

}
