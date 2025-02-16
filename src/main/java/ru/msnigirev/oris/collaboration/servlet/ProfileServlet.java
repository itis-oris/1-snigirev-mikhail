package ru.msnigirev.oris.collaboration.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.msnigirev.oris.collaboration.dto.UserDto;
import ru.msnigirev.oris.collaboration.service.ProjectService;
import ru.msnigirev.oris.collaboration.service.UserService;

import java.io.IOException;

@WebServlet(name = "profile", value = "/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserService userService = (UserService) req.getServletContext().getAttribute("userService");
        ProjectService projectService = (ProjectService) req.getServletContext().getAttribute("projectService");
        UserDto user = userService.getUserDto((String) req.getSession().getAttribute("username"));
        req.setAttribute("projects", projectService.getAllByAdmin(user.getId()));
        req.setAttribute("name", user.getPublicName());
        req.setAttribute("avatar", user.getAvatarUrl());
        req.setAttribute("username", user.getUsername());
        req.getRequestDispatcher("/views/profile.jsp").forward(req, res);
        /*
          <h2><%= request.getAttribute("name") %></h2>
`                <img src="<%= request.getAttribute("avatar") %>" alt="Аватар" class="avatar"> <!-- Замените на путь к вашей аватарке -->
          <h3><%= request.getAttribute("username") %></h3>

          <h2>Самые популярные проекты:</h2>
          <ul class="popular-projects">
            <li>Популярный проект 1</li>
            <li>Популярный проект 2</li>
          </ul>
            <li>Популярный проект 3</li>

          <p><%= request.getAttribute("description") %></p>
          <h2>Описание профиля:</h2>
         */
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

}
