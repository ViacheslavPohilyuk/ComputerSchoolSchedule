package org.computer.school.schedule.app.servlet.authorization;

import org.computer.school.schedule.app.datastorage.db.sql.person.reading.PersonContext;
import org.computer.school.schedule.app.entity.interfaces.person.Person;
import org.computer.school.schedule.app.servlet.WebPageMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by mac on 26.02.17.
 */
public class AuthorizationServlet extends HttpServlet {
    private HttpServletRequest request;
    private HttpServletResponse response;

    private WebPageMessage m;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        m = new WebPageMessage(response);

        checkAuthorizationData();
    }

    /** Getting strings from login and password fields
     *  than reading persons' data from the db
     *  and checking that the person with required
     *  authorization data is in the database
     */
    private void checkAuthorizationData () {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        List<Person> persons;
        PersonContext personContext = new PersonContext();
        persons = personContext.executeRead();

        String personEmail;
        String personPassword;
        boolean authorized = false;
        for (Person p : persons) {
            personEmail = p.getEmail();
            personPassword = p.getPassword();
            if (login.equals(personEmail) &
                    password.equals(personPassword)) {
                m.message("You successfully logged in!");
                authorized = true;
                break;
            }
        }
        if (!authorized)
            m.message("Invalid login or password!");
    }
}
