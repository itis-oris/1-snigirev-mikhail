package ru.msnigirev.oris.collaboration.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.msnigirev.oris.collaboration.service.UserService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebServlet(value = "/logout", name = "logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        doPost(req, res);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserService userService = (UserService) req.getServletContext().getAttribute("userService");
        try {
            String token = (String) req.getSession().getAttribute("csrf_token");
            req.getSession().removeAttribute("csrf_token");
            Optional<Cookie> csrfToken =
                    Arrays.stream(req.getCookies())
                            .filter(cookie -> cookie.getName().equals("csrf_token"))
                            .findFirst();

            if (csrfToken.isPresent()) {
                Cookie cookie = new Cookie("csrf_token", null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                res.addCookie(cookie);
            }

            userService.deleteCsrfToken(token);

            res.sendRedirect(req.getContextPath() + "/login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
