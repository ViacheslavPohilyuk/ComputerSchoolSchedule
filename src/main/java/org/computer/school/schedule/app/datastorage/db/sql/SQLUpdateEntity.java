package org.computer.school.schedule.app.datastorage.db.sql;

import java.sql.PreparedStatement;

/**
 * Created by mac on 15.02.17.
 */
public interface SQLUpdateEntity<T> {
    /**
     * This method create PreparedStatement object and
     * retrieves other methods for changing some data in the database.
     * Also it closes connection to the database.
     * @return count of updated rows in the db
     */
    T updateProcessing();

    /**
     * Return some sql-query that needed for update some data in the database
     * @return String object with sql-query
     */
    String sql();

    /**
     * Setting parameters for precompiled sql-query
     * @param preparedStatement PreparedStatement object, that containing
     *                          some precompiled sql-query
     */
    void setEntityParameters(PreparedStatement preparedStatement);
}
