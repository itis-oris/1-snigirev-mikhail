package ru.msnigirev.oris.collaboration.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.msnigirev.oris.collaboration.service.UserService;

import java.io.IOException;

@WebServlet(name = "register", value = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserService userService = (UserService) req.getServletContext().getAttribute("userService");
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        try {
            String username = req.getParameter("username");
            String publicName = req.getParameter("publicName");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String password = bCrypt.encode(req.getParameter("password"));

            userService.registerNewUser(username, publicName, email, phone, password);

            res.sendRedirect(req.getContextPath() + "/login");
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            res.sendRedirect(req.getContextPath() + "/registerpage");
        }
    }
}