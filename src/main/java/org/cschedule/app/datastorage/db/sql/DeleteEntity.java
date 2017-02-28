package org.cschedule.app.datastorage.db.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by mac on 16.02.17.
 */
public class DeleteEntity {
    private Integer[] entitiesID;
    private String firstSQLQuery;
    private String secondSQLQuery;
    private Connection conn;

    public DeleteEntity(Integer[] entitiesID, String firstSQLQuery, String secondSQLQuery, Connection conn) {
        this.entitiesID = entitiesID;
        this.firstSQLQuery = firstSQLQuery;
        this.secondSQLQuery = secondSQLQuery;
        this.conn = conn;
    }

    public Integer[] deleteEntity() {
        Integer[] deletedEntities = null;
        try (PreparedStatement preparedEntityDelete = conn.prepareStatement(firstSQLQuery);
             PreparedStatement preparedSecondEntityDelete = conn.prepareStatement(secondSQLQuery)) {
            for (Integer id : entitiesID) {
                setDeleteParameters(preparedEntityDelete, id);
                setDeleteParameters(preparedSecondEntityDelete, id);
                preparedEntityDelete.addBatch();
                preparedSecondEntityDelete.addBatch();
            }

            int[] delInt = preparedEntityDelete.executeBatch();

            /** Convert the massive with int elements to
             *  the massive with Integer elements */
            int delLength = delInt.length;
            deletedEntities = new Integer[delLength];
            for (int d = 0; d < delLength; d++)
                deletedEntities[d] = delInt[d];

            preparedSecondEntityDelete.executeBatch();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return deletedEntities; // count of deleted rows in the db
    }

    private void setDeleteParameters(PreparedStatement preparedStatement, Integer entityID) throws SQLException {
        preparedStatement.setInt(1, entityID);
    }
}
