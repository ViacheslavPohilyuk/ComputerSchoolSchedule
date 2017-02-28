package org.cschedule.app.entity.pojo.course;

import org.cschedule.app.entity.interfaces.course.Course;
import org.cschedule.app.entity.interfaces.course.LectureRoom;
import org.cschedule.app.entity.interfaces.course.Lecture;
import org.cschedule.app.entity.interfaces.course.TimeTable;

/**
 * Created by stephenvolf on 14/12/16.
 */
public class CoursePOJO implements Course {
    public String title;
    public TimeTable timeTable;

    public CoursePOJO(String title, TimeTable timeTable) {
        this.title = title;
        this.timeTable = timeTable;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public TimeTable timeTable() {
        return timeTable;
    }

    @Override
    public void print(){
        System.out.println("Course: " + this.title);
        for (Lecture lecture : this.timeTable.allLectures()) {
            System.out.println("Lecture " + lecture.title());
            System.out.println("start: " + lecture.startTime());
            System.out.println("end: " + lecture.endTime());
            System.out.println("description: " + lecture.description());
            LectureRoom room = lecture.lectureRoom();
            System.out.println("room: " + room.floor() + " floor, " + room.number() + ", " + room.description());
        }
    }
}
