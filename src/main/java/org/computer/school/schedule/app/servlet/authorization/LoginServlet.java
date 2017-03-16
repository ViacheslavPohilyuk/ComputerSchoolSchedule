package org.computer.school.schedule.app.servlet.authorization;

import org.computer.school.schedule.ContextListener;
import org.computer.school.schedule.app.datastorage.db.DBConnection;
import org.computer.school.schedule.app.datastorage.db.sql.person.reading.PersonContext;
import org.computer.school.schedule.app.entity.interfaces.person.Person;
import org.computer.school.schedule.app.servlet.WebPageMessage;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

/**
 * Created by mac on 26.02.17.
 */
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Getting connection to the database */
        DBConnection dbConnection = (DBConnection) getServletContext().getAttribute("dbConnection");
        Connection databaseConnection = dbConnection.getConnection();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        List<Person> persons;
        PersonContext personContext = new PersonContext(databaseConnection);
        persons = personContext.executeRead();

        String personEmail;
        String personPassword;
        boolean authorized = false;
        for (Person p : persons) {
            personEmail = p.getEmail();
            personPassword = p.getPassword();
            if (login.equals(personEmail) &&
                    password.equals(personPassword)) {
                /* Creating the new cookie */
                Cookie loginCookie = new Cookie("login", login);
                /* Setting cookie to expiry in 30 mins */
                loginCookie.setMaxAge(30*60);
                response.addCookie(loginCookie);
                response.sendRedirect("login.jsp");
                authorized = true;
                break;
            }
        }
        if (!authorized) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font><br>");
            rd.include(request, response);
        }
    }
}
