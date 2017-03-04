package org.computer.school.schedule.app.servlet.entities;

import org.computer.school.schedule.app.servlet.WebPageMessage;
import org.computer.school.schedule.app.servlet.entities.operations.ServletEntityOperations;
import org.computer.school.schedule.app.servlet.entities.operations.CoursesServletOperations;
import org.computer.school.schedule.app.servlet.entities.operations.PersonsServletOperations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by mac on 22.02.17.
 */
public class ServletURIProcessing extends HttpServlet implements ServletURIProcessingInterface {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String[] pathTokens;

    private ServletEntityOperations servletOperation;
    private WebPageMessage m;

    public ServletURIProcessing() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        m = new WebPageMessage(response);
        URIProcessing();
    }

    @Override
    public void URIProcessing() {
        Map<String, String[]> pathParams;
        String pathInfo = request.getPathInfo();
        if (!pathInfo.isEmpty()) {
            pathTokens = pathInfo.split("/");
            if (pathTokens.length > 1) {
                pathParams = request.getParameterMap();
                switch(pathTokens[1]) {
                    case "courses": {
                        servletOperation =
                                new CoursesServletOperations(
                                        response, pathParams, pathTokens);
                        break;
                    }
                    case "persons": {
                        servletOperation =
                                new PersonsServletOperations(
                                        response, pathParams, pathTokens);
                        break;
                    }
                    default:
                        m.message("No such entity!");
                }
                choiceServletOperation();
            }
        }
    }

    @Override
    public void choiceServletOperation() {
        switch (pathTokens[2]) {
            case "read": {
                servletOperation.read();
                break;
            }
            case "insert": {
                servletOperation.insert();
                break;
            }
            case "delete": {
                servletOperation.delete();
                break;
            }
            default: {
                m.message("No such operation!");
            }
        }
    }
}
