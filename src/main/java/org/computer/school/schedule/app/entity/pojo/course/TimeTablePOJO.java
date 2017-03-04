package org.computer.school.schedule.app.entity.pojo.course;

import org.computer.school.schedule.app.entity.interfaces.course.Lecture;
import org.computer.school.schedule.app.entity.interfaces.course.TimeTable;

import java.util.Date;
import java.util.List;

/**
 * Created by stephenvolf on 14/12/16.
 */
public class TimeTablePOJO implements TimeTable {
    public List<Lecture> lectures;

    public TimeTablePOJO(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public List<Lecture> allLectures() {
        return lectures;
    }

    @Override
    public List<Lecture> lecturesFrom(Date date) {
        return lectures;
    }

    @Override
    public List<Lecture> lecturesTo(Date date) {
        return lectures;
    }

}
