package org.computer.school.schedule.app.entity.operations.date;

import org.computer.school.schedule.app.entity.interfaces.course.Course;
import org.computer.school.schedule.app.entity.interfaces.course.Lecture;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mac on 16.02.17.
 */
public class DateFilter {
    public List<Lecture> periodLecture (Course course, Date from, Date to) {
        long begin = from.getTime();
        long end = to.getTime();

        List<Lecture> lectures = course.timeTable().allLectures();
        List<Lecture> lecturesPeriod = new ArrayList<>();

        long current_time;
        for (Lecture l : lectures) {
            current_time = l.startTime().getTime();
            if(begin <= current_time && current_time <= end)
                lecturesPeriod.add(l);
        }
        return lecturesPeriod;
    }

    public Lecture forthcomingLecture (Course course) {
        List<Lecture> lectures = course.timeTable().allLectures();

        int i = 0;
        int i_min = 0;

        long current_lecture_time;
        long min = lectures.get(0).startTime().getTime();
        for (Lecture l : lectures) {
            current_lecture_time = l.startTime().getTime();
            if(min > current_lecture_time) {
                min = current_lecture_time;
                i_min = i;
            }
            i++;
        }
        return lectures.get(i_min);
    }
}
