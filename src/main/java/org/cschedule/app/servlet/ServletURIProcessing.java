package org.cschedule.app.servlet;

import org.cschedule.app.servlet.entities.course.CoursesServletOperations;
import org.cschedule.app.servlet.entities.person.PersonServletOperations;
import org.cschedule.app.servlet.entities.WebPageMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by mac on 22.02.17.
 */
public class ServletURIProcessing implements ServletURIProcessingInterface{
    private HttpServletRequest req;
    private HttpServletResponse resp;

    private String[] pathTokens;
    private String entityName;

    private CoursesServletOperations coursesServletOperations = null;
    private PersonServletOperations personsServletOperations = null;

    private WebPageMessage m;
    public ServletURIProcessing(HttpServletRequest req, HttpServletResponse resp,
                                String entityName) {
        this.req = req;
        this.resp = resp;
        this.entityName = entityName;

        m = new WebPageMessage(resp);
    }

    @Override
    public void URIProcessing() {
        Map<String, String[]> pathParams;

        String pathInfo = req.getPathInfo();
        if (!pathInfo.isEmpty()) {
            pathTokens = pathInfo.split("/");
            if (pathTokens.length > 1) {
                pathParams = req.getParameterMap();
                if (pathTokens[1].equals("database")) {
                    switch(entityName) {
                        case "courses": {
                            coursesServletOperations =
                                    new CoursesServletOperations(
                                            resp, pathParams, pathTokens);
                            break;
                        }
                        case "persons": {
                            personsServletOperations =
                                    new PersonServletOperations(
                                            resp, pathParams, pathTokens);
                            break;
                        }
                        default:
                            m.message("No such entity!");
                    }
                    EntityServletURIProcessing();
                }
            }
        }
    }

    @Override
    public void EntityServletURIProcessing() {
        boolean servletChoice = (coursesServletOperations != null);

        switch (pathTokens[2]) {
            case "read": {
                if(servletChoice) {
                    coursesServletOperations.read();
                } else {
                    personsServletOperations.read();
                }
                break;
            }
            case "insert": {
                if(servletChoice) {
                    coursesServletOperations.insert();
                } else {
                    personsServletOperations.insert();
                }
                break;
            }
            case "delete": {
                if(servletChoice) {
                    coursesServletOperations.delete();
                } else {
                    personsServletOperations.delete();
                }
                break;
            }
            default: {
                m.message("No such operation!");
            }
        }
    }
}
