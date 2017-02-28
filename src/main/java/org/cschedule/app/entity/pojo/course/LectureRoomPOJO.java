package org.cschedule.app.entity.pojo.course;

import org.cschedule.app.entity.interfaces.course.LectureRoom;

/**
 * Created by stephenvolf on 14/12/16.
 */
public class LectureRoomPOJO implements LectureRoom {
    public int floor;
    public int number;
    public String description;

    public LectureRoomPOJO(){}

    public LectureRoomPOJO(int floor, int number, String description) {
        this.floor = floor;
        this.number = number;
        this.description = description;
    }

    @Override
    public int floor() {
        return floor;
    }

    @Override
    public int number() {
        return number;
    }

    @Override
    public String description() {
        return description;
    }
}
