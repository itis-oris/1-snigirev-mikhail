package ru.msnigirev.oris.collaboration.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import ru.msnigirev.oris.collaboration.entity.Project;
import ru.msnigirev.oris.collaboration.dto.ProjectDto;
import ru.msnigirev.oris.collaboration.entity.User;
import ru.msnigirev.oris.collaboration.repository.impl.InstituteRepository;
import ru.msnigirev.oris.collaboration.repository.impl.SubjectRepository;
import ru.msnigirev.oris.collaboration.repository.impl.TeacherRepository;
import ru.msnigirev.oris.collaboration.repository.interfaces.ProjectAdminsRepository;
import ru.msnigirev.oris.collaboration.repository.interfaces.ProjectRepository;
import ru.msnigirev.oris.collaboration.repository.interfaces.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Builder
public class ProjectServiceImpl implements ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final InstituteRepository instituteRepository;
    private final ProjectAdminsRepository projectAdminsRepository;
    @Override
    public Project getById(int id) {
        Project project = projectRepository.getById(id).orElse(null);
        if (project == null) return null;
        Set<User> admins = projectAdminsRepository.getAdmins(project.getId())
                .stream()
                .map(userRepository::getById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        project.setAdmins(admins);
        return project;
    }

    @Override
    public ProjectDto getDtoById(int id) {
        Project project = getById(id);
        if (project == null) return null;
        /*
        public class ProjectDto {
    private int id;
    private String name;
    private String description;
    private int creatorId;
    private String teacher;
    private String institute;
    private String subject;
    private int year;
    // url + file
    private Map<String, File> projectStructure;
    private Set<User> admins;
         */
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .creatorId(project.getCreatorId())
                .teacher(teacherRepository.getById(project.getTeacherId()))
                .institute(instituteRepository.getById(project.getTeacherId()))
                .subject(subjectRepository.getById(project.getTeacherId()))
                .year(project.getYear())
                .admins(project.getAdmins())
                .folder(project.getFolder())
                .avatar(project.getAvatar())
                .build();
    }

    @Override
    public List<Project> getAllByAdmin(int adminId) {
        List<Integer> projects = projectAdminsRepository.getProjects(adminId);
        return projects.stream()
                .map(projectRepository::getById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public void addNewProject(ProjectDto projectDto) {
        int subjectId = subjectRepository.getByName(projectDto.getSubject());
        if (subjectId == 0) {
            subjectRepository.add(projectDto.getSubject());
            subjectId = subjectRepository.getByName(projectDto.getSubject());
        }
        int instituteId = instituteRepository.getByName(projectDto.getInstitute());
        if (instituteId == 0) {
            instituteRepository.add(projectDto.getInstitute());
            instituteId = instituteRepository.getByName(projectDto.getInstitute());
        }
        int teacherId = teacherRepository.getByName(projectDto.getTeacher());
        if (teacherId == 0) {
            teacherRepository.add(projectDto.getTeacher());
            teacherId = teacherRepository.getByName(projectDto.getTeacher());
        }

        Project project = Project.builder()
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .creatorId(projectDto.getCreatorId())
                .subjectId(subjectId)
                .instituteId(instituteId)
                .teacherId(teacherId)
                .year(projectDto.getYear())
                .folder(projectDto.getFolder())
                .admins(projectDto.getAdmins())
                .avatar(projectDto.getAvatar())
                .build();
        projectRepository.create(project);
        projectAdminsRepository.addNewRelation(projectRepository.getMaxId(), project.getCreatorId());
    }

    @Override
    public List<Project> getAll(int offset, int size) {
       return projectRepository.getAll(offset, size);
    }
    @Override
    public List<Project> searchByName(String name) {
        return projectRepository.searchByName(name);
    }

    @Override
    public boolean addAdmin(String username, int projectId) {
        User user = userRepository.getByUsername(username).orElse(null);
        if (user == null) return false;
        return projectAdminsRepository.addNewRelation(projectId, user.getId());
    }

    @Override
    public void addAvatar(String avatarUrl, int id) {
        projectRepository.addAvatar(avatarUrl, id);
    }
    @Override
    public boolean isAdmin(int projectId, String username) {
        int userId = userRepository.getIdByUsername(username);
       return projectAdminsRepository.getAdmins(projectId).stream()
               .anyMatch(id -> id == userId);
    }
    @Override
    public boolean isCreator(int projectId, String username) {
        Optional<Project> project = projectRepository.getById(projectId);
        if (!project.isPresent()) return false;
        String usernameById = userRepository.getUsernameById(project.get().getCreatorId());
        if (usernameById == null) return false;
        return usernameById.equals(username);
    }

}
