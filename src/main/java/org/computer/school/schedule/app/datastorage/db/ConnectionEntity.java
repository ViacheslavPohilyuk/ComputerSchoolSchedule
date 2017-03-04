package org.computer.school.schedule.app.datastorage.db;

import java.sql.Connection;

/**
 * Created by mac on 15.02.17.
 */
public interface ConnectionEntity {
    /**
     * Retrieving methods for connection to the database
     * @return Connection object, this one will store
     *         the connection with some properties to the database
     */
    Connection getConnection();

    /**
     * Reading file with properties for connecting to required database.
     * And write these date to the object with type Properties
     */
    void setProperties();

    /**
     * Retrieving method that called getConnection of the DriverManager object
     * for loading database URL and Properties object and appropriating the
     * result of this method to the Connection object
     */
    void connectDatabase();

    /**
     * Closing the connection to the database
     * @param conn Connection object that will be closed
     */
    void close(Connection conn);
}
