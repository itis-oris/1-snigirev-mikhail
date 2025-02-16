package ru.msnigirev.oris.collaboration.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.msnigirev.oris.collaboration.entity.User;
import ru.msnigirev.oris.collaboration.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "users", value = "/users")
public class UsersPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();

        UserService userService = (UserService) servletContext.getAttribute("userService");
        List<User> users = userService.getAllUsers();
        req.setAttribute("users", users);
        try {
            req.getRequestDispatcher("/views/users.jsp").forward(req, res);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
