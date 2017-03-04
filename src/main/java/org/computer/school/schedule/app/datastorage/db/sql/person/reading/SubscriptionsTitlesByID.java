package org.computer.school.schedule.app.datastorage.db.sql.person.reading;

import org.computer.school.schedule.app.datastorage.db.sql.SQLReadEntity;
import org.computer.school.schedule.app.datastorage.db.sql.RowsTableCount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by mac on 16.02.17.
 */
public class SubscriptionsTitlesByID implements SQLReadEntity<String[][]>  {
    private Connection conn;
    private List<Integer> idList;

    public SubscriptionsTitlesByID(List<Integer> idList, Connection conn) throws SQLException {
        this.conn = conn;
        this.idList = idList;

        conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    @Override
    public String[][] executeRead () {
        ResultSet resultSet = null;  // dummy ResultSet
        return extractResult(resultSet);
    }

    @Override
    public String[][] extractResult(ResultSet resultSet) {
        String[][] subscriptions = new String[idList.size()][];
        RowsTableCount rowscount = new RowsTableCount();

        try (PreparedStatement psGetSubsName = conn.prepareStatement(sql())) {
            int i = 0;
            for (Integer id : idList) {
                setParameters(psGetSubsName,id);
                resultSet = psGetSubsName.executeQuery();

                int j = 0;
                subscriptions[i] = new String[rowscount.rowsCount(resultSet)];
                while(resultSet.next()) {
                    subscriptions[i][j] = resultSet.getString("title");
                    j++;
                }
                i++;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }


    private void setParameters(PreparedStatement statement, int id) throws SQLException {
        statement.setInt(1, id);
    }

    @Override
    public String sql() {
        return "Select title from courses " +
                "Where id in (Select course_id from subscriptions " +
                "Where user_id = (Select id from users " +
                "Where id = ?))";
    }
}
