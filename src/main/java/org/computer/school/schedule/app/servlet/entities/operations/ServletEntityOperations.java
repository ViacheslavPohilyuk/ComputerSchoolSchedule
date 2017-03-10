package org.computer.school.schedule.app.servlet.entities.operations;

import java.text.ParseException;

/**
 * Created by mac on 22.02.17.
 */
public interface ServletEntityOperations {
    /**  Tokens of the URI-path:
     *   pathTokens[0] URL-pattern of the servlet
     *   pathTokens[1] entity ("persons", "courses")
     *   pathTokens[2] Operations (read, insert, delete)
     *   pathTokens[3] token that using in some operations (for example read)
     */

    /** Reading lectures of the course from the database
     *
     *  Courses
     *  all
     *  URL for reading all lectures of the course:
     *  operation/courses/read/all?coursename=%title of the course%
     *  lecture
     *  URL for reading one lecture by specifying its title and name of the course:
     *  operation/courses/read/lecture/bytitle?coursename=%title of the course%&
     *                                         lecturename=%title of the lecture%
     *  URL for getting a forthcoming lecture of some course:
     *  operation/courses/read/lecture/forthcoming?coursename=%title of the course%
     *  period
     *  Date format: dd.MM.yyyy-HH:mm
     *  URL for getting all lectures of a course in some period of time:
     *  operation/courses/read/period?coursename=%title of the course%&
     *                                from=%first date%&
     *                                to=%second date%
     *
     *  Persons
     *  all
     *  URL for reading all persons:
     *  operation/persons/read/all
     *  person
     *  URL for reading persons by first name, surname and patronymic:
     *  operation/persons/read/person?fname=%persons's first name%&
     *                                surname=%persons's surname%&
     *                                patronymic=%persons's patronymic%
     */
    void read();

    /** Inserting new entity to the database
     *
     *  Courses
     *
     *  Date format: dd.MM.yyyy-HH:mm
     *
     *  URL for lecture inserting:
     *  operation/courses/insert?coursename=%title of the course%&
     *                           startTime=%date when lecture begin%&
     *                           roomFloor=%floor where the room of the lecture situated%&
     *                           roomNumber=%number of this room%&
     *                           roomDesc=%path to the room%&
     *                           title=%name of the lecture%&
     *                           desc=%description what will be told on the lecture%
     *
     *  Persons
     *  URL for person inserting:
     *  operation/persons/insert?fname=%person's first name%&
     *                           surname=%person's surname%&
     *                           patronymic=%person's patronymic%&
     *                           type=%person's status%&
     *                           email=%person's email%&
     *                           password=%person's password%&
     *                           subscription1=%title1%&subscription2=%title2%&...&subscriptionN=%titleN%
     *
     *  @exception ParseException if using wrong date format.
     */
    void insert();

    /** Deleting entity in the database
     *
     *  Courses
     *  URL for deleting lecture:
     *  operation/courses/delete?coursename=%title of the course%&
     *                           lecturename=%title of the lecture%
     *
     *  Persons
     *  URL for deleting person:
     *  operation/persons/delete?fname=%persons's first name%&
     *                           surname=%persons's surname%&
     *                           patronymic=%persons's patronymic%
     */
    void delete();
}
