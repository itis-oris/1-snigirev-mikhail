package ru.msnigirev.oris.collaboration.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.msnigirev.oris.collaboration.dto.ProjectDto;
import ru.msnigirev.oris.collaboration.service.ProjectService;
import ru.msnigirev.oris.collaboration.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@MultipartConfig
@WebServlet(name = "createProject", value = "/createProject")
public class ProjectCreation extends HttpServlet {
    private static final String folders = "/Users/misha/IdeaProjects/ITIS/Collaboration/src/main/webapp/projects/folders/";
    private static final String serverFolders = "/projects/folders/";
    private static final String avatars = "/Users/misha/IdeaProjects/ITIS/Collaboration/src/main/webapp/projects/avatars/";
    private static final String avatarsServer = "/projects/avatars/";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String projectName = getFormField(req, "projectName");
        String projectDescription = getFormField(req, "description");
        String teacherName = getFormField(req, "teacherName");
        String subjectName = getFormField(req, "subjectName");
        String instituteName = getFormField(req, "instituteName");
        int year = Integer.parseInt(getFormField(req, "year"));

        UserService userService = (UserService) req.getServletContext().getAttribute("userService");

        // Получаем ID создателя проекта через юзернейм в сессии
        int creatorId = userService.getUser((String) req.getSession().getAttribute("username")).getId();

        String avatarUrl = null;
        if (req.getPart("avatar") != null && req.getPart("avatar").getSize() > 0) {
            avatarUrl = saveAvatar(req.getPart("avatar"), req.getServletContext().getRealPath("/"), projectName);
        }
        File folder = new File(req.getServletContext().getRealPath("/") + serverFolders.substring(1)
                + UUID.randomUUID() + "_" + projectName);
        if (!folder.exists()) folder.mkdirs();

        System.out.println(folder.getAbsolutePath());
        // Собираем инфу в ProjectDto
        ProjectDto projectDto = ProjectDto.builder()
                .name(projectName)
                .description(projectDescription)
                .creatorId(creatorId)
                .teacher(teacherName)
                .subject(subjectName)
                .institute(instituteName)
                .year(year)
                .folder(serverFolders + folder.getName())
                .avatar(avatarUrl)
                .build();
        File constFolder = new File(folders, folder.getName());
        System.out.println(constFolder.getAbsolutePath());
        if (!constFolder.exists()) constFolder.mkdirs();
        // Сохраняем проект (например, в базе данных)
        ProjectService projectService = (ProjectService) req.getServletContext().getAttribute("projectService");
        projectService.addNewProject(projectDto);

        // Перенаправляем на страницу проекта или на страницу со списком проектов
        res.sendRedirect(req.getContextPath() + "/profile");
    }

    // Метод для сохранения аватара в файловую систему и возврата его URL
    private String saveAvatar(Part avatarPart, String contextPath, String projectName) throws IOException {
        File uploadDirFile = new File(avatars);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();  // Создаем директорию, если ее нет
        }

        // Генерируем уникальное имя для аватара
        String avatarName = avatarPart.getSubmittedFileName();
        String avatarFileName = UUID.randomUUID() + "_" + projectName +
                avatarName.substring(avatarName.lastIndexOf('.'));
        File avatarFile = new File(uploadDirFile, avatarFileName);
        File avatarFileServer = new File(contextPath + avatarsServer, avatarFileName);
        // Сохраняем аватар
        avatarPart.write(avatarFile.getAbsolutePath());

        Path result = Files.copy(avatarFile.toPath(), avatarFileServer.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println(result);
        // Возвращаем URL файла
        return avatarsServer + avatarFileName;
    }
    private String getFormField(HttpServletRequest req, String fieldName) throws IOException, ServletException {
        Part part = req.getPart(fieldName);
        if (part != null) {
            return new String(part.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
        }
        return null;
    }
}