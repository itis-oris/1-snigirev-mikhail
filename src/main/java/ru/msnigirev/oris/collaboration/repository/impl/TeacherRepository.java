package ru.msnigirev.oris.collaboration.repository.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.msnigirev.oris.collaboration.repository.interfaces.ProjectTagRepository;

import javax.sql.DataSource;

public class TeacherRepository implements ProjectTagRepository {

    private final JdbcTemplate jdbcTemplate;

    public TeacherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String SELECT_TEACHER_BY_ID = "SELECT name FROM teachers WHERE id = ?";
    private static final String SELECT_ID_BY_NAME = "SELECT id FROM teachers WHERE name = ?";
    private static final String INSERT_TEACHER = "INSERT INTO teachers (name) VALUES (?)";

    @Override
    public String getById(int id) {
        return jdbcTemplate.queryForObject(SELECT_TEACHER_BY_ID, new Object[]{id}, String.class);
    }

    @Override
    public int getByName(String name) {
        try {
            return jdbcTemplate.queryForObject(SELECT_ID_BY_NAME, new Object[]{name}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public void add(String name) {
        jdbcTemplate.update(INSERT_TEACHER, name);
    }
}
