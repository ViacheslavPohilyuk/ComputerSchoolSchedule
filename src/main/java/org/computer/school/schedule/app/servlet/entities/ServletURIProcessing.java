package org.computer.school.schedule.app.servlet.entities;

import org.computer.school.schedule.app.datastorage.db.DBConnection;
import org.computer.school.schedule.app.servlet.WebPageMessage;
import org.computer.school.schedule.app.servlet.entities.operations.ServletEntityOperations;
import org.computer.school.schedule.app.servlet.entities.operations.CoursesServletOperations;
import org.computer.school.schedule.app.servlet.entities.operations.PersonsServletOperations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
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
    private Connection databaseConnection;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        m = new WebPageMessage(response);

        /* Getting connection to the database */
        DBConnection dbConnection = (DBConnection) getServletContext().getAttribute("dbConnection");
        databaseConnection = dbConnection.getConnection();

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
                String entity = pathTokens[1];
                if(!entity.isEmpty()) {
                    switch (entity) {
                        case "courses": {
                            servletOperation =
                                    new CoursesServletOperations(
                                            databaseConnection, response, pathParams, pathTokens);
                            break;
                        }
                        case "persons": {
                            servletOperation =
                                    new PersonsServletOperations(
                                            databaseConnection, response, pathParams, pathTokens);
                            break;
                        }
                        default:
                            m.message("No such entity!");
                    }
                    choiceServletOperation();
                }
            }
        }
    }

    @Override
    public void choiceServletOperation() {
        String operation = pathTokens[2];
        if(!operation.isEmpty()) {
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
}
