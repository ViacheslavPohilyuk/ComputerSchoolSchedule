package org.computer.school.schedule.app.datastorage.db.sql.course.reading;

import org.computer.school.schedule.app.entity.pojo.course.LectureRoomPOJO;
import org.computer.school.schedule.app.datastorage.db.sql.SQLReadEntity;
import org.computer.school.schedule.app.entity.interfaces.course.Lecture;
import org.computer.school.schedule.app.entity.pojo.course.LecturePOJO;
import org.computer.school.schedule.app.datastorage.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by stephenvolf on 11/01/17.
 */
public class CourseContext implements SQLReadEntity<List<Lecture>> {
    private String courseName;
    private List<Lecture> result = new ArrayList<>();

    private DBConnection DBconnect = new DBConnection();
    private Connection conn = DBconnect.getConnection();

    /** If we want get lectures of a course
     *  by their title, we appropriate to object
     *  <code>sqlLecture</code> the value - " and l.title = ?"
     *
     *  else if it's necessary to get all lectures of a course
     *  we leave this String object empty
     */
    private String sqlLecture = "";
    private String lectureName = "";

    /* Constructor for getting lectures by their title */
    public CourseContext(String courseName, String lectureName) {
        this.courseName = courseName;
        this.lectureName = lectureName;

        /* This String object will concatenate
           with with string in the sql() method
         */
        sqlLecture = " and l.title = ?";
    }

    /* Constructor for getting all lectures */
    public CourseContext(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public List<Lecture> executeRead() {
        try (PreparedStatement preparedCourse = conn.prepareStatement(sql())) {
             setParameters(preparedCourse);
             ResultSet resultCourse = preparedCourse.executeQuery();
             result = extractResult(resultCourse);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBconnect.close(conn);
        }
        return result;
    }

    @Override
    public String sql() {
        return  " select l.start_time," +
                " l.end_time," +
                " lr.floor as roomFloor," +
                " lr.`number` as roomNumber," +
                " lr.description as roomDescription," +
                " l.title," +
                " l.description" +

                " from" +
                " lectures as l join lecture_rooms as lr " +
                " on" +
                " l.lecture_room_id = lr.id join course_lectures as cl " +
                " on" +
                " l.id = cl.lecture_id join courses as c " +
                " on" +
                " cl.course_id = c.id" +
                " where c.title = ?" + sqlLecture;
    }

    private void setParameters(PreparedStatement statement) throws SQLException {
        statement.setString(1, courseName);

        /** This is statement can be performed
            only if we want to get some lecture
            by its title
         */
        if(!sqlLecture.isEmpty()) {
            statement.setString(2, lectureName);
        }
    }

    @Override
    public List<Lecture> extractResult(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                Date startDate = resultSet.getDate("start_time");
                Date endDate = resultSet.getDate("end_time");
                int floor = resultSet.getInt("roomFloor");
                int roomNumber = resultSet.getInt("roomNumber");
                String roomDescription = resultSet.getString("roomDescription");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LectureRoomPOJO lectureRoom = new LectureRoomPOJO(floor, roomNumber, roomDescription);
                Lecture lecture = new LecturePOJO(startDate, endDate, lectureRoom, title, description);
                result.add(lecture);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
