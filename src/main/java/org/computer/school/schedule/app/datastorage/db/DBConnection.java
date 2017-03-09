package org.computer.school.schedule.app.datastorage.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by mac on 30.01.17.
 */
public class DBConnection {
    private Connection conn = null;

    public DBConnection (String url, String userName, String password) {
        try {
            conn = DriverManager.getConnection(url, userName, password);
        }
        catch (SQLException e) {
            System.err.println("Database connection error!");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void close() {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Database closing error!");
                e.printStackTrace();
            }
        }
    }
}
