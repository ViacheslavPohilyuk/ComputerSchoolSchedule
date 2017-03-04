package org.computer.school.schedule.app.datastorage.db.sql.course.updating;

import org.computer.school.schedule.app.datastorage.db.sql.SQLUpdateEntity;
import org.computer.school.schedule.app.datastorage.db.sql.DeleteEntity;
import org.computer.school.schedule.app.datastorage.db.DBConnection;
import org.computer.school.schedule.app.datastorage.db.sql.RowsTableCount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mac on 16.02.17.
 */
public class DeleteLecture implements SQLUpdateEntity<Integer[]> {
    private String courseTitle;
    private String lectureTitle;

    private DBConnection DBconnect = new DBConnection();
    private Connection conn = DBconnect.getConnection();

    public DeleteLecture(String courseTitle, String lectureTitle) {
        this.courseTitle = courseTitle;
        this.lectureTitle = lectureTitle;
    }

    @Override
    public Integer[] updateProcessing() {
        ResultSet resultSet;
        Integer[] deleteCount = null;
        RowsTableCount rowscount = new RowsTableCount();

        /** Deleting lectures by its title and by the title of its course */
        try (PreparedStatement preparedSelectID = conn.prepareStatement(sql())) {
            /** Getting ids of entities **/
            setEntityParameters(preparedSelectID);
            resultSet = preparedSelectID.executeQuery();
            Integer[] lecturesID = new Integer[rowscount.rowsCount(resultSet)];

            int i = 0;
            while(resultSet.next()) {
                lecturesID[i] = resultSet.getInt("lecture_id");
                i++;
            }

            /** Deleting entities **/
            String sqlLectureDelete =   "Delete from lectures " +
                                        "Where id = ?";
            String sqlLectureIDDelete = "Delete from course_lectures " +
                                        "Where lecture_id = ?";
            DeleteEntity deleteEntity =
                    new DeleteEntity(lecturesID, sqlLectureDelete, sqlLectureIDDelete, conn);

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
            preparedStatement.setString(1, courseTitle);
            preparedStatement.setString(2, lectureTitle);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String sql() {
        return  "Select lecture_id from course_lectures " +
                "Where course_id in (Select id from courses " +
                                    "Where title = ?) " +
                                "and " +
                      "lecture_id in (Select id from lectures " +
                                     "Where title = ?) ";
    }
}
