package org.cschedule.app.servlet.authorization;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mac on 26.02.17.
 */
public class AuthorizationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthorizationServletOperations auth =
                                    new AuthorizationServletOperations(req, resp);
        auth.checkAuthorizationData();
    }
}
