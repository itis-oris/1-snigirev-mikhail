package ru.msnigirev.oris.collaboration.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.msnigirev.oris.collaboration.entity.User;
import ru.msnigirev.oris.collaboration.repository.interfaces.ProjectTagRepository;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class SubjectRepository implements ProjectTagRepository {

    private final JdbcTemplate jdbcTemplate;
    public SubjectRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private static final String SELECT_SUBJECT_BY_ID = "SELECT name FROM subjects WHERE id = ?";
    private static final String SELECT_ID_BY_NAME = "SELECT id FROM subjects WHERE name = ?";
    private static final String INSERT_SUBJECT = "INSERT INTO subjects (name) VALUES (?)";

    @Override
    public String getById(int id) {
        return jdbcTemplate.queryForObject(SELECT_SUBJECT_BY_ID, new Object[]{id}, String.class);
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
        jdbcTemplate.update(INSERT_SUBJECT, name);
    }
}

