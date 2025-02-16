package ru.msnigirev.oris.collaboration.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DuplicateKeyException;
import ru.msnigirev.oris.collaboration.service.ProjectService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "addAdmin", value = "/addAdmin")
public class AddAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ProjectService projectService = (ProjectService) req.getServletContext().getAttribute("projectService");
        int id = Integer.parseInt(req.getParameter("id"));
        String username = req.getParameter("username");
        String message = "";
        try {
            boolean success = projectService.addAdmin(username, id);
            if (success) message = String.format("Успех %s теперь администратор проекта", username);
            else message = String.format("Пользователя %s не существует", username);


        } catch (DuplicateKeyException e) {
            message = "Уже есть такой админ";

        }
        message = URLEncoder.encode(message, StandardCharsets.UTF_8);
        res.sendRedirect(req.getContextPath() + String.format("/project?id=%s&message=%s", id, message));
    }
}
