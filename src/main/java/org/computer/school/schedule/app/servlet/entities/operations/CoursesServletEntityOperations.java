package org.computer.school.schedule.app.servlet.entities.operations;

import org.computer.school.schedule.app.entity.pojo.course.LectureRoomPOJO;
import org.computer.school.schedule.app.entity.interfaces.course.Course;
import org.computer.school.schedule.app.entity.pojo.course.CoursePOJO;
import org.computer.school.schedule.app.datastorage.db.sql.course.reading.ReadCourse;
import org.computer.school.schedule.app.datastorage.db.sql.course.updating.DeleteLecture;
import org.computer.school.schedule.app.datastorage.db.sql.course.updating.InsertLecture;
import org.computer.school.schedule.app.entity.interfaces.course.Lecture;
import org.computer.school.schedule.app.entity.operations.date.DateFilter;
import org.computer.school.schedule.app.entity.pojo.course.LecturePOJO;

import org.computer.school.schedule.app.entity.pojo.course.TimeTablePOJO;
import org.computer.school.schedule.app.servlet.WebPageMessage;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 22.02.17.
 */
public class CoursesServletEntityOperations implements ServletEntityOperations {
    private Course course;

    private HttpServletResponse resp;
    private String[] pathTokens;
    private Map<String, String[]> pathParams;

    private WebPageMessage m;

    public CoursesServletEntityOperations(HttpServletResponse resp,
                                          Map<String, String[]> pathParams,
                                          String[] pathTokens) {
        this.resp = resp;
        this.pathTokens = pathTokens;
        this.pathParams = pathParams;

        m = new WebPageMessage(resp);
    }

    @Override
    public void read() {
        String courseName = pathParams.get("coursename")[0];
        ReadCourse readCourse = new ReadCourse(courseName); // methods for reading lectures
                                                            // of a course from the db

        String readingOperation = pathTokens[3];

        switch (readingOperation) {
            case "all": {
                course = new CoursePOJO(courseName, new TimeTablePOJO(readCourse.allLecturesCourse()));
                m.jsonMessage(course);
                break;
            }
            case "lecture": {
                String lectureOperation = pathTokens[4];
                switch (lectureOperation) {
                    case "bytitle": {
                        String lectureName = pathParams.get("lecturename")[0];
                        List<Lecture> lectures = readCourse.lecturesByTitle(lectureName);
                        m.jsonMessage(lectures);
                        break;
                    }
                    case "forthcoming": {
                        DateFilter dateFilter = new DateFilter();
                        course = new CoursePOJO(courseName, new TimeTablePOJO(readCourse.allLecturesCourse()));
                        Lecture lecture = dateFilter.forthcomingLecture(course);
                        m.jsonMessage(lecture);
                        break;
                    }
                    default: {
                        m.message("Wrong URL token for reading a lecture!");
                    }
                }
                break;
            }
            case "period": {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy-HH:mm");
                DateFilter dateFilter = new DateFilter();

                course = new CoursePOJO(courseName, new TimeTablePOJO(readCourse.allLecturesCourse()));
                try {
                    Date from = sdf.parse(pathParams.get("from")[0]);
                    Date to = sdf.parse(pathParams.get("to")[0]);
                    List<Lecture> lectures =
                            dateFilter.periodLecture(course, from, to);
                    m.jsonMessage(lectures);
                }
                catch (ParseException e) {
                    m.message("Wrong date format!");
                    e.printStackTrace();
                }
                break;
            }
            default: {
                m.message("No such reading operation!");
            }
        }
    }

    @Override
    public void insert() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy-HH:mm");

            String coursename = pathParams.get("coursename")[0];
            Date startTime = sdf.parse(pathParams.get("startTime")[0]);
            Date endTime = sdf.parse(pathParams.get("endTime")[0]);
                int roomFloor = Integer.parseInt(pathParams.get("roomFloor")[0]);
                int roomNumber = Integer.parseInt(pathParams.get("roomNumber")[0]);
                String roomDesc = pathParams.get("roomDesc")[0];
            String title = pathParams.get("title")[0];
            String desc = pathParams.get("desc")[0];

            InsertLecture insertLecture =
                    new InsertLecture(
                            coursename,
                            new LecturePOJO(
                                    startTime,
                                    endTime,
                                    new LectureRoomPOJO(
                                            roomFloor,
                                            roomNumber,
                                            roomDesc
                                                       ),
                                    title,
                                    desc
                                            )
                                    );

            Integer updateRowsCount = insertLecture.updateProcessing();
            m.message("{\"Rows inserted\": " + "\"" + updateRowsCount + "\"}");

        } catch (ParseException e) {
            m.message("Wrong date format!");
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        String courseName = pathParams.get("coursename")[0];
        String lectureName = pathParams.get("lecturename")[0];

        DeleteLecture deleteLecture =
                        new DeleteLecture(courseName, lectureName);

        Integer[] deletedRows = deleteLecture.updateProcessing();

        /** Report of deleted lectures from the database */
        Integer lectureDeletedCount = 0;
        for (Integer d : deletedRows)
            lectureDeletedCount += d;

        m.message("{\"Lectures deleted\": " + "\"" + lectureDeletedCount + "\"}");
    }
}
