package org.computer.school.schedule.app.entity.interfaces.course;

import java.util.Date;

public interface Lecture {
    Date startTime();
    Date endTime();
    LectureRoom lectureRoom();
    String title();
    String description();
    void print();
}
