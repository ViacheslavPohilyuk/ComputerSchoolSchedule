package org.cschedule;

import org.cschedule.app.datastorage.db.sql.course.reading.ReadCourse;
import org.cschedule.app.entity.interfaces.course.Lecture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.List;

public class Main {
    private final static Logger LL = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ParseException {
        ReadCourse readCourse = new ReadCourse("java12");
        List<Lecture> lectures = readCourse.lecturesByTitle("Maven");
        for (Lecture l: lectures) {
            l.print();
        }

    }
}
