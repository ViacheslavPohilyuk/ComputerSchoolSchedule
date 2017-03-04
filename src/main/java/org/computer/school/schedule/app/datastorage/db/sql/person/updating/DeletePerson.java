package org.computer.school.schedule.app.datastorage.db.sql.person.updating;

import org.computer.school.schedule.app.datastorage.db.sql.SQLUpdateEntity;
import org.computer.school.schedule.app.datastorage.db.DBConnection;
import org.computer.school.schedule.app.datastorage.db.sql.DeleteEntity;
import org.computer.school.schedule.app.datastorage.db.sql.RowsTableCount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mac on 16.02.17.
 */
public class DeletePerson implements SQLUpdateEntity<Integer[]> {
    private String fname;
    private String surname;
    private String patronymic;

    private DBConnection DBconnect = new DBConnection();
    private Connection conn = DBconnect.getConnection();

    public DeletePerson(String fname, String surname, String patronymic) {
        this.fname = fname;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    @Override
    public Integer[] updateProcessing() {
        ResultSet resultSet;
        Integer[] deleteCount = null;
        RowsTableCount rowscount = new RowsTableCount();
        try (PreparedStatement preparedSelectID = conn.prepareStatement(sql())) {
            /** Getting ids of users **/
            setEntityParameters(preparedSelectID);
            resultSet = preparedSelectID.executeQuery();
            Integer[] personID = new Integer[rowscount.rowsCount(resultSet)];

            int i = 0;
            while(resultSet.next()) {
                personID[i] = resultSet.getInt("id");
                i++;
            }

            /** Deleting users and their subscriptions **/
            String sqlPersonDelete = "Delete from users " +
                                     "Where id = ?";
            String sqlSubscriptionDelete = "Delete from subscriptions " +
                                           "Where user_id = ?";
            DeleteEntity deleteEntity =
                    new DeleteEntity(personID, sqlPersonDelete, sqlSubscriptionDelete, conn);

            deleteCount = deleteEntity.deleteEntity();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBconnect.close(conn);
        }
        return deleteCount;
    }

    @Override
    public void setEntityParameters(PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(1, fname);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, patronymic);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String sql() {
            return  "Select id from users " +
                    "Where fname = ? and surname = ? and patronymic = ?";
    }
}
