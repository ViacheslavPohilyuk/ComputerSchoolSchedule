package org.cschedule.app.datastorage.json.reading.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cschedule.app.datastorage.json.reading.JsonFileRead;
import org.cschedule.app.entity.interfaces.course.Course;
import org.cschedule.app.entity.interfaces.course.Lecture;
import org.cschedule.app.entity.pojo.course.CoursePOJO;
import org.cschedule.app.entity.pojo.course.LecturePOJO;
import org.cschedule.app.entity.pojo.course.TimeTablePOJO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mac on 11.02.17.
 */
public class JsonCourse {
    private List<Lecture> lectureList = null;
    private ObjectMapper mapper = new ObjectMapper();
    private final Logger LL = LoggerFactory.getLogger(JsonCourse.class);

   // @Override
    public Course course(String courseName) {
        JsonFileRead jread = new JsonFileRead("courses/" + courseName);

        try {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy-HH:mm");
            mapper.setDateFormat(df);
            Lecture[] lectures = mapper.readValue(jread.readJson(), LecturePOJO[].class);
            lectureList = Arrays.asList(lectures);
        } catch (IOException e) {
            LL.error("Could not deserialize json", e);
            e.printStackTrace();
        }

        Course result = new CoursePOJO(courseName, new TimeTablePOJO(lectureList));
        return result;
    }
}
