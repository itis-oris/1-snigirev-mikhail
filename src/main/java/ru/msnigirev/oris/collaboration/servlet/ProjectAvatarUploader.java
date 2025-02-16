package ru.msnigirev.oris.collaboration.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.msnigirev.oris.collaboration.entity.Project;
import ru.msnigirev.oris.collaboration.entity.User;
import ru.msnigirev.oris.collaboration.service.ProjectService;
import ru.msnigirev.oris.collaboration.service.UserService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@MultipartConfig
@WebServlet("/projectavatarupload")
public class ProjectAvatarUploader extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "/Users/misha/IdeaProjects/ITIS/Collaboration/src/main/webapp/projects/avatars";
    private static final String DOWNLOAD_DIRECTORY = "/projects/avatars";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ProjectService projectService = (ProjectService) req.getServletContext().getAttribute("projectService");

        int id = Integer.parseInt(req.getParameter("projectId"));
        Project project = projectService.getById(id);
        if (project.getAvatar() != null) {
            String avatarName = project.getAvatar().substring(project.getAvatar().lastIndexOf('/'));
            File deleteFile = new File(UPLOAD_DIRECTORY, avatarName);
            deleteFile.delete();
            deleteFile = new File(DOWNLOAD_DIRECTORY, avatarName);
            deleteFile.delete();

        }
        Part filePart = req.getPart("avatar");
        if (filePart == null || filePart.getSubmittedFileName().isEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Файл не загружен");
            return;
        }

        String originalFileName = filePart.getSubmittedFileName();
        String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

        File uploadDir = new File(UPLOAD_DIRECTORY);

        File fileToSave = new File(uploadDir, uniqueFileName);

        // Временный путь, из которого будут стираться аватарки при редиплое
        String avatarPath = getServletContext().getRealPath(DOWNLOAD_DIRECTORY) + File.separator + uniqueFileName;
        File avatarFile = new File(avatarPath);

        try (InputStream inputStream = filePart.getInputStream()) {
            Files.copy(inputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Files.copy(fileToSave.toPath(), avatarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            String avatarUrl = req.getContextPath() + DOWNLOAD_DIRECTORY + "/" + uniqueFileName;



            projectService.addAvatar(avatarUrl, id);

            res.sendRedirect(req.getContextPath() + "/project?id=" + id);
        } catch (IOException e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка сохранения файла");
        }
    }
}
