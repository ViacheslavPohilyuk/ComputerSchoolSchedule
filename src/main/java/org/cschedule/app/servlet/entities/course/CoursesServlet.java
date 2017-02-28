package org.cschedule.app.servlet.entities.course;
import org.cschedule.app.servlet.ServletURIProcessing;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CoursesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletURIProcessing servletURIProcessing = new ServletURIProcessing(req, resp, "courses");
        servletURIProcessing.URIProcessing();
    }
}

