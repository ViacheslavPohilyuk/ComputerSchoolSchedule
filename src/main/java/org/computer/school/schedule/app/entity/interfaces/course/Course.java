package org.computer.school.schedule.app.entity.interfaces.course;

/**
 * Created by stephenvolf on 12/12/16.
 */
public interface Course {
    String title();
    TimeTable timeTable();
    void print();
}
