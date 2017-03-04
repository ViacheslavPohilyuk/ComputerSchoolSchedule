package org.computer.school.schedule.app.datastorage.db.sql.course.updating;

import org.computer.school.schedule.app.datastorage.db.sql.SQLUpdateEntity;
import org.computer.school.schedule.app.entity.interfaces.course.Lecture;
import org.computer.school.schedule.app.datastorage.db.DBConnection;

import java.sql.*;

/**
 * Created by mac on 01.02.17.
 */
public class InsertLecture implements SQLUpdateEntity<Integer> {
    /* Lecture data */
    private final Timestamp startTime;
    private final Timestamp endTime;
        /* The room of the lecture */
        private final int roomFloor;
        private final int roomNumber;
        private final String roomDesc;
    private final String title;
    private final String description;
    private final String courseName;

    private DBConnection DBconnect = new DBConnection();
    private Connection conn = DBconnect.getConnection();

    public InsertLecture(String courseName, Lecture lecture) {
        this.startTime = new Timestamp(lecture.startTime().getTime());
        this.endTime = new Timestamp(lecture.endTime().getTime());
            this.roomFloor = lecture.lectureRoom().floor();
            this.roomNumber = lecture.lectureRoom().number();
            this.roomDesc = lecture.lectureRoom().description();
        this.title = lecture.title();
        this.description = lecture.description();
        this.courseName = courseName;
    }

    @Override
    public Integer updateProcessing() {
        int updateCount = 0;
        try (PreparedStatement preparedLecture = conn.prepareStatement(sql(), Statement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedCourseLectureID = conn.prepareStatement(sqlCourseLecturesIDStatement())) {

             setEntityParameters(preparedLecture);
             preparedLecture.executeUpdate();

             /** Get id of the inserted lecture */
             int key = 0;
             ResultSet generatedKeys = preparedLecture.getGeneratedKeys();
             while(generatedKeys.next())
                  key = generatedKeys.getInt(1);

             setCourseLectureIDParameters(preparedCourseLectureID, key);

             /** Count of inserted rows in the db */
             updateCount = preparedCourseLectureID.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBconnect.close(conn);
        }
        return updateCount;
    }

    @Override
    public String sql() {
        return "insert into lectures(start_time, end_time, lecture_room_id, title, description)" +
                " VALUES(?,?," +
                                 "(Select id from lecture_rooms " +
                                  "Where floor = ? and number = ? and description = ?)," +
                        "?,?)";
    }

    private String sqlCourseLecturesIDStatement() {
        return "insert into course_lectures(course_id, lecture_id) " +
                "VALUES ((select id from courses where title = ?), ?)";
    }

    @Override
    public void setEntityParameters(PreparedStatement statement) {
        try {
            statement.setTimestamp(1, startTime);
            statement.setTimestamp(2, endTime);
            statement.setInt(3, roomFloor);
            statement.setInt(4, roomNumber);
            statement.setString(5, roomDesc);
            statement.setString(6, title);
            statement.setString(7, description);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCourseLectureIDParameters(PreparedStatement statement, int key) throws SQLException {
        statement.setString(1, courseName);
        statement.setInt(2, key);
    }
}
