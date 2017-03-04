package org.computer.school.schedule.app.datastorage.db.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mac on 11.02.17.
 */
public class RowsTableCount {
    public int rowsCount(ResultSet rs) {
        int rows_count = 0;
        try {
            if (rs.last()) {
                rows_count = rs.getRow();
                rs.beforeFirst();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rows_count;
    }
}
