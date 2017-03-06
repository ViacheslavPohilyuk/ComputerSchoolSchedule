package org.computer.school.schedule.app.datastorage.db;

import java.io.FileInputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by mac on 30.01.17.
 */
public class DBConnection implements ConnectionEntity {
    private Properties prop = new Properties();
    private Connection conn = null;

    public synchronized Connection getConnection() {
            setProperties();
            connectDatabase();
            return conn;
    }

    public void setProperties() {
        try (FileInputStream input = new FileInputStream("src/main/resources/dbprop.properties")) {
            prop.load(input);
        } catch (IOException e) {
            System.err.println("Can't open file with the properties of the connection to the database");
            e.printStackTrace();
        }
    }

    public void connectDatabase() {
        try {
            //String ssl = "?verifyServerCertificate=false&useSSL=true";
            conn = DriverManager.getConnection(
                    "jdbc:mysql://rds-mysql-computerschoolschedule.cmrrktnryoch.eu-central-1.rds.amazonaws.com" +
                                        ":3306" +
                                        "/ComputerSchoolSchedule", prop);
        } catch (SQLException e) {
            System.err.println("Database connection error!");
            e.printStackTrace();
        }
    }

    public void close(Connection conn) {
        try {
            conn.close();
        }
        catch (SQLException e) {
            System.err.println("Database closing error!");
            e.printStackTrace();
        }
    }
}
