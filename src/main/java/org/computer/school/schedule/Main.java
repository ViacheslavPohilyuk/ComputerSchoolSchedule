package org.computer.school.schedule;

import org.computer.school.schedule.app.datastorage.db.sql.course.reading.CourseContext;
import org.computer.school.schedule.app.datastorage.db.sql.person.reading.PersonContext;
import org.computer.school.schedule.app.entity.interfaces.course.Lecture;
import org.computer.school.schedule.app.entity.interfaces.person.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.List;

public class Main {
    private final static Logger LL = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ParseException {
        /*CourseContext courseContext = new CourseContext("java12");
        List<Lecture> lectures = courseContext.executeRead();
        for (Lecture l : lectures) {
            l.print();
        }

        List<Person> persons;
        PersonContext personContext = new PersonContext();
        persons = personContext.executeRead();
        for (Person p : persons) {
            p.print();
        }*/
    }
}
