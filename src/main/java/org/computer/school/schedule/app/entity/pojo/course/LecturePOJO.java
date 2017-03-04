package org.computer.school.schedule.app.entity.pojo.course;

import org.computer.school.schedule.app.entity.interfaces.course.LectureRoom;
import org.computer.school.schedule.app.entity.interfaces.course.Lecture;

import java.util.Date;

/**
 * Created by stephenvolf on 14/12/16.
 */
public class LecturePOJO implements Lecture {
    public Date startTime;
    public Date endTime;
    public LectureRoomPOJO lectureRoom;
    public String title;
    public String description;

    public LecturePOJO(){}

    public LecturePOJO(Date startTime, Date endTime, LectureRoomPOJO lectureRoom, String title, String description) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.lectureRoom = lectureRoom;
        this.title = title;
        this.description = description;
    }

    @Override
    public Date startTime() {
        return startTime;
    }

    @Override
    public Date endTime() {
        return endTime;
    }

    @Override
    public LectureRoom lectureRoom() {
        return lectureRoom;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public void print() {
        System.out.println("Lecture " + title);
        System.out.println("start: " + startTime);
        System.out.println("end: " + endTime);
        System.out.println("description: " + description);
        System.out.println("Lecture room: ");
        System.out.println(" room: " + lectureRoom.floor() +
                           " floor: " + lectureRoom.number() +
                           " description: " + lectureRoom.description());
    }
}
