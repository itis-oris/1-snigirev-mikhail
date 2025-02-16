package ru.msnigirev.oris.collaboration.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.msnigirev.oris.collaboration.entity.Project;
import ru.msnigirev.oris.collaboration.service.ProjectService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "search", value = "/search")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectService projectService = (ProjectService) req.getServletContext().getAttribute("projectService");
        List<Project> projects = null;
        if (req.getParameter("text") != null) {
            projects = projectService.searchByName(req.getParameter("text"));
        }
        req.setAttribute("projects", projects);

        req.getRequestDispatcher("views/search.jsp").forward(req, resp);

    }
}
