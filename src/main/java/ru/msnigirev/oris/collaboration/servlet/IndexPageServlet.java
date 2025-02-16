package ru.msnigirev.oris.collaboration.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.msnigirev.oris.collaboration.repository.interfaces.ProjectRepository;
import ru.msnigirev.oris.collaboration.service.ProjectService;

import java.io.IOException;

@WebServlet(name = "index", value = "/index")
public class IndexPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ProjectService projectService =
                (ProjectService) req.getServletContext().getAttribute("projectService");
        req.setAttribute("projects", projectService.getAll(0, 20));
        req.getRequestDispatcher("/views/index.jsp").forward(req, res);
    }
}
