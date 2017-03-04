package org.computer.school.schedule.app.datastorage.db.sql.course.reading;

import org.computer.school.schedule.app.entity.interfaces.course.Lecture;

import java.util.List;

/**
 * Created by mac on 26.02.17.
 */
public class ReadCourse {
    private String courseName;
    private CourseContext courseContext;

    public ReadCourse(String courseName) {
        this.courseName = courseName;
    }

    public List<Lecture> allLecturesCourse() {
        courseContext = new CourseContext(courseName);
        return courseContext.executeRead();
    }

    public List<Lecture> lecturesByTitle(String lectureName) {
        courseContext = new CourseContext(courseName, lectureName);
        return courseContext.executeRead();
    }
}
