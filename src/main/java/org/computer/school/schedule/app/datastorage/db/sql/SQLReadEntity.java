package org.computer.school.schedule.app.datastorage.db.sql;

import java.sql.ResultSet;

/**
 * Created by mac on 15.02.17.
 */
public interface SQLReadEntity<T> {
    /**
     * This method create PreparedStatement object and
     * retrieves other methods for reading data about the
     * required entity from the database.
     * @return объект, либо множество объектов описывающие сущность
     */
    T executeRead();

    /**
     * Return some sql-query that needed for read some data in the database
     * @return String object with sql-query
     */
    String sql();

    /**
     * Reading data about some entity from the ResultSet object
     * @param resultSet ResultSet object with data of some entity
     * @return object of some entity or set of the one
     */
    T extractResult(ResultSet resultSet);
}
