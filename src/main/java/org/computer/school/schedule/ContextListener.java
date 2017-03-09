package org.computer.school.schedule;

import org.computer.school.schedule.app.datastorage.db.DBConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        String url = servletContext.getInitParameter("url");
        String user_name = servletContext.getInitParameter("user_name");
        String password = servletContext.getInitParameter("password");
        String database = servletContext.getInitParameter("database");
        DBConnection dbConnection = new DBConnection(url + "/" + database, user_name, password);
        servletContext.setAttribute("dbConnection", dbConnection);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        DBConnection dbConnection = (DBConnection) servletContext.getAttribute("dbConnection");
        dbConnection.close();
    }
}
