package ru.msnigirev.oris.collaboration.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.msnigirev.oris.collaboration.service.UserService;

import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*")
public class AuthorisationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (req.getServletPath().equals("/login") ||
                req.getServletPath().equals("/registerpage") ||
                req.getServletPath().equals("/register") ||
                req.getServletPath().equals("/usercheck")) {
            chain.doFilter(req, res);
        } else {
            getResource(req, res, chain);
        }
    }
    private void getResource(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        if (req.getSession(false) != null) {
            chain.doFilter(req, res);
        } else {

            Cookie[] cookies = req.getCookies();

            Cookie csrfToken = null;
            if (cookies != null) {
               csrfToken = Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals("csrf_token"))
                        .findFirst()
                        .orElse(null);
            }
            if (csrfToken != null) {
                UserService userService = (UserService) req.getServletContext().getAttribute("userService");
                String actualToken = csrfToken.getValue();
                if (userService.csrfTokenExists(actualToken)) {
                    String username = userService.getUsernameByToken(csrfToken.getValue());
                    req.getSession().setAttribute("username", username);
                    chain.doFilter(req, res);
                } else {
                    deleteCookie(csrfToken, res);
                }
            } else {
                res.sendRedirect("/collaboration/login");
            }
        }
    }


    private void deleteCookie(Cookie csrfToken, HttpServletResponse res){
        Cookie cookie = csrfToken;
        cookie.setPath("/");
        cookie.setValue("");
        cookie.setMaxAge(0);
        res.addCookie(cookie);
    }
}
