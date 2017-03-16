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
public class ServletURIProcessing extends HttpServlet {
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

    /** In this method occurring the URI fragmentation
     *
     * Getting URI tokens
     * For partition some URI on tokens we use method that called <code>split</code>
     * of the String class with value of the parameter of this method  - "/".
     * The result of these operation is massive of String objects <code>pathTokens</code>
     *
     * Getting URI parameters
     * For getting parameters (values that are placed after symbol "?" in URI)
     * of URL we use getParameterMap method for HTTPServletRequest
     * object <code>req</code>, and put the result of this method
     * to the <code>Map<String, String[]> pathParams</code>
     *
     * Operations of servlets
     * Now we put <code>pathTokens</code> and <code>pathParams</code>
     * to the constructor of <code>CoursesServletOperations</code> object
     * or <code>PersonsServletOperations</code> object.
     * And retrieving method <code>choiceServletOperation</code>
     * for use operation (read, insert, delete) of required entity
     */
    private void URIProcessing() {
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

    /** Using one of three operations Read, Insert and Delete
     *  of either Course entity or Person entity.
     *  If variable of the interface ServletEntityOperations
     *  that named <code>servletOperation</code> contains
     *  <code>CoursesServletOperations</code> object
     *  we will using methods for courses
     *  Otherwise we use methods of the
     *  <code>PersonsServletOperations</code> object
     *  for persons
     */
    private void choiceServletOperation() {
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
