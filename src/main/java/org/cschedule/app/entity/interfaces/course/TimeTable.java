package org.cschedule.app.entity.interfaces.course;

import java.util.Date;
import java.util.List;

public interface TimeTable {
    List<Lecture> allLectures();
    List<Lecture> lecturesFrom(Date date);
    List<Lecture> lecturesTo(Date date);
}
