package ru.msnigirev.oris.collaboration.servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.msnigirev.oris.collaboration.dto.ProjectDto;
import ru.msnigirev.oris.collaboration.service.ProjectService;
import ru.msnigirev.oris.collaboration.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(name = "project", value = "/project")
public class ProjectServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserService userService = (UserService) req.getServletContext().getAttribute("userService");
        ProjectService projectService = (ProjectService) req.getServletContext().getAttribute("projectService");

        ProjectDto project = projectService.getDtoById(Integer.parseInt(req.getParameter("id")));
        String creatorName = userService.getUsernameById(project.getCreatorId());
        String username = (String) req.getSession().getAttribute("username");

        req.setAttribute("projectName", project.getName());
        req.setAttribute("avatarUrl", project.getAvatar());
        req.setAttribute("creatorName", creatorName);
        req.setAttribute("description", project.getDescription());
        req.setAttribute("teacherName", project.getTeacher());
        req.setAttribute("subjectName", project.getSubject());
        req.setAttribute("instituteName", project.getInstitute());
        req.setAttribute("year", project.getYear());
        req.setAttribute("id", project.getId());
        req.setAttribute("message", req.getParameter("message") == null ? "" : req.getParameter("message"));
        req.setAttribute("isAdmin", projectService.isAdmin(project.getId(), username));
        req.setAttribute("isCreator", projectService.isCreator(project.getId(), username));
        String folder = req.getServletContext().getRealPath("/") + project.getFolder().substring(1);

        if (folder == null || folder.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.setCharacterEncoding("UTF-8");
            res.setContentType("text/plain");
            res.getWriter().write("В проекте нет данных");
            return;
        }

        File folderFile = new File(folder);
        if (folderFile.exists() && !folderFile.isDirectory()) {
            System.out.println(folder);
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.setCharacterEncoding("UTF-8");
            res.setContentType("text/plain");
            res.getWriter().write("Ваша папка, оказывается, не папка вовсе");
            return;
        }

        if (folderFile.exists()) {
            JsonObject json = generateJson(folderFile, Paths.get("/projects/folders"));
            req.setAttribute("json", json.toString().replace("\"", "\\\""));
        }

        req.getRequestDispatcher("/views/project.jsp").forward(req, res);
    }



    private JsonObject generateJson(File folder, Path contextPath) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("path", contextPath.toString());

        JsonArray filesArray = new JsonArray();
        JsonArray foldersArray = new JsonArray();

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    JsonObject fileObject = new JsonObject();
                    fileObject.addProperty("name", file.getName());
                    fileObject.addProperty("path", contextPath.resolve(file.getName()).toString());
                    filesArray.add(fileObject);
                } else if (file.isDirectory()) {
                    Path subContextPath = contextPath.resolve(file.getName());
                    foldersArray.add(generateJson(file, subContextPath));
                }
            }
        }

        jsonObject.add("files", filesArray);
        jsonObject.add("folders", foldersArray);

        return jsonObject;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
