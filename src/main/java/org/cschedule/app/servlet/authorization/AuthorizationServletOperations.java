package org.cschedule.app.servlet.authorization;

import org.cschedule.app.datastorage.db.sql.person.reading.PersonContext;
import org.cschedule.app.entity.interfaces.person.Person;
import org.cschedule.app.servlet.entities.WebPageMessage;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 27.02.17.
 */
public class AuthorizationServletOperations {
    private HttpServletRequest req;
    private HttpServletResponse resp;

    WebPageMessage m;
    AuthorizationServletOperations (HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;

        m = new WebPageMessage(resp);
    }

    /** Getting strings from login and password fields
     *  than reading persons' data from the db
     *  and checking that the person with required
     *  authorization data is in the database
     */
    void checkAuthorizationData () {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

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
