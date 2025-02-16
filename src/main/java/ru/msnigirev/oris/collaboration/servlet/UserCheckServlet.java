package ru.msnigirev.oris.collaboration.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.msnigirev.oris.collaboration.entity.User;
import ru.msnigirev.oris.collaboration.service.UserService;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "usercheck", value = "/usercheck")
public class UserCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserService userService = (UserService) req.getServletContext().getAttribute("userService");
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        try{
            String password = req.getParameter("password");
            String username = req.getParameter("username");

            User user = userService.getUser(username);
            if (user != null && bCrypt.matches(password, user.getPassword())) {
                String csrfToken = UUID.randomUUID().toString();
                String rememberMe = req.getParameter("rememberMe");

                if (rememberMe != null) {
                    Cookie cookie = new Cookie("csrf_token", csrfToken);
                    cookie.setMaxAge(60*60*24*3);
                    res.addCookie(cookie);
                    userService.addCsrfToken(csrfToken, username);
                }
                req.getSession().setAttribute("username", username);

                res.sendRedirect("/collaboration/profile");
            } else {
                res.sendRedirect("/collaboration/login");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
