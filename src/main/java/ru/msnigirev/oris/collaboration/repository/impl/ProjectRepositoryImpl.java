package ru.msnigirev.oris.collaboration.repository.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.msnigirev.oris.collaboration.entity.Project;
import ru.msnigirev.oris.collaboration.repository.interfaces.ProjectRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ProjectRepositoryImpl implements ProjectRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Project> rowMapper;
    private final static String GET_BY_ID = "select * from projects where id = ?";
    private final static String CREATE = "INSERT INTO projects " +
            "(name, description, creator_id, subject_id, institute_id, teacher_id, year, avatar_url, folder) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String NEXT_ID = "SELECT MAX(id) FROM projects;";
    private final static String GET_ALL_WITH_LIMITATIONS = "SELECT * FROM projects OFFSET ? LIMIT ?";
    private final static String SEARCH_BY_NAME = "SELECT * FROM projects WHERE name LIKE CONCAT('%', ?, '%')";
    private final static String ADD_AVATAR = "UPDATE projects SET avatar_url = ? WHERE id = ?";

    public ProjectRepositoryImpl(DataSource dataSource, RowMapper<Project> rowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.rowMapper = rowMapper;
    }

    @Override
    public int getMaxId() {
        try {
            return jdbcTemplate.queryForObject(NEXT_ID, Integer.class);
        }  catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public void create(Project data) {
        jdbcTemplate.update(CREATE,
                data.getName(),
                data.getDescription(),
                data.getCreatorId(),
                data.getSubjectId(),
                data.getInstituteId(),
                data.getTeacherId(),
                data.getYear(),
                data.getAvatar(),
                data.getFolder());
    }

    @Override
    public Optional<Project> getById(Integer id) {

        List<Project> projects = jdbcTemplate.query(GET_BY_ID, rowMapper, id);
        return optionalSingleResult(projects);
    }

    private Optional<Project> optionalSingleResult(List<Project> projects) {
        if (projects.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1);
        } else {
            return projects.stream().findAny();
        }
    }
    @Override
    public List<Project> getAll(int offset, int size) {
        return jdbcTemplate.query(GET_ALL_WITH_LIMITATIONS, rowMapper, offset, size);
    }
    @Override
    public List<Project> searchByName(String name){
        return jdbcTemplate.query(SEARCH_BY_NAME, rowMapper, name);
    }

    @Override
    public void addAvatar(String avatarUrl, int id) {
       jdbcTemplate.update(ADD_AVATAR, avatarUrl, id);
    }

}
