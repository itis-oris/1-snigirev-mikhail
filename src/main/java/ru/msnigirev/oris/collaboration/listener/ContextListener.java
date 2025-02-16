package ru.msnigirev.oris.collaboration.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.flywaydb.core.Flyway;
import ru.msnigirev.oris.collaboration.config.DataSourceConfiguration;
import ru.msnigirev.oris.collaboration.repository.impl.*;
import ru.msnigirev.oris.collaboration.repository.interfaces.ProjectAdminsRepository;
import ru.msnigirev.oris.collaboration.repository.interfaces.ProjectRepository;
import ru.msnigirev.oris.collaboration.repository.interfaces.UserRepository;
import ru.msnigirev.oris.collaboration.repository.mapper.ProjectRowMapper;
import ru.msnigirev.oris.collaboration.repository.mapper.UserRowMapper;
import ru.msnigirev.oris.collaboration.service.ProjectService;
import ru.msnigirev.oris.collaboration.service.ProjectServiceImpl;
import ru.msnigirev.oris.collaboration.service.UserService;
import ru.msnigirev.oris.collaboration.service.UserServiceImpl;

import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DataSourceConfiguration configuration =
                new DataSourceConfiguration(properties);

        Flyway flyway = Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(configuration.hikariDataSource())
                .load();
//        flyway.migrate();
        UserRepository userRepository =
                new UserRepositoryImpl(configuration.hikariDataSource(), new UserRowMapper());

        UserService userService = new UserServiceImpl(userRepository);

        ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute("userService", userService);

        ProjectRepository projectRepository =
                new ProjectRepositoryImpl(configuration.hikariDataSource(), new ProjectRowMapper());

        TeacherRepository teacherRepository =
                new TeacherRepository(configuration.hikariDataSource());
        SubjectRepository subjectRepository =
                new SubjectRepository(configuration.hikariDataSource());
        InstituteRepository instituteRepository =
                new InstituteRepository(configuration.hikariDataSource());

        ProjectAdminsRepository projectAdminsRepository =
                new ProjectAdminsRepositoryImpl(configuration.hikariDataSource());
        ProjectService projectService = ProjectServiceImpl.builder()
                        .userRepository(userRepository)
                        .projectRepository(projectRepository)
                        .teacherRepository(teacherRepository)
                        .subjectRepository(subjectRepository)
                        .instituteRepository(instituteRepository)
                        .projectAdminsRepository(projectAdminsRepository)
                        .build();
//        ProjectService projectService = new ProjectServiceImpl(userRepository, projectRepository,
//                teacherRepository, subjectRepository, instituteRepository, projectAdminsRepository);

        servletContext.setAttribute("projectService", projectService);
    }
}
