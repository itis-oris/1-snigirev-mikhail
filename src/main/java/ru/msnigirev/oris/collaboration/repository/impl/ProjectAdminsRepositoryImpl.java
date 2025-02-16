package ru.msnigirev.oris.collaboration.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.msnigirev.oris.collaboration.repository.interfaces.ProjectAdminsRepository;

import javax.sql.DataSource;
import java.util.List;

public class ProjectAdminsRepositoryImpl implements ProjectAdminsRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectAdminsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final static String GET_ALL_ADMINS_BY_ID =  "select user_id from project_admins where project_id = ?";
    private final static String GET_ALL_PROJECTS_BY_ID =  "select project_id from project_admins where user_id = ?";
    private final static String ADD_NEW_RELATION =  "INSERT INTO project_admins (project_id, user_id) VALUES (?, ?);";
    @Override
    public List<Integer> getAdmins(int projectId) {
        return jdbcTemplate.queryForList(GET_ALL_ADMINS_BY_ID, new Object[]{projectId}, Integer.class);
    }

    @Override
    public List<Integer> getProjects(int userId) {
        return jdbcTemplate.queryForList(GET_ALL_PROJECTS_BY_ID, new Object[]{userId}, Integer.class);
    }

    @Override
    public boolean addNewRelation(int projectId, int userId) {
        return jdbcTemplate.update(ADD_NEW_RELATION, projectId, userId) == 1;
    }
}
