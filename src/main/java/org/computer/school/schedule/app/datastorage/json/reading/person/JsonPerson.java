package org.computer.school.schedule.app.datastorage.json.reading.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.computer.school.schedule.app.datastorage.json.reading.JsonFileRead;
import org.computer.school.schedule.app.entity.interfaces.person.Person;
import org.computer.school.schedule.app.entity.pojo.person.PersonPOJO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mac on 11.02.17.
 */
public class JsonPerson {
    private List<Person> PersonList = null;
    private ObjectMapper mapper = new ObjectMapper();
    private final Logger LL = LoggerFactory.getLogger(JsonPerson.class);

    // @Override
    public List<Person> person() {
        JsonFileRead jread = new JsonFileRead("person/person");

        try {
            ObjectMapper mapper = new ObjectMapper();
            Person[] personalities = mapper.readValue(jread.readJson(), PersonPOJO[].class);
            PersonList = Arrays.asList(personalities);
        } catch (IOException e) {
            LL.error("Could not deserialize json", e);
            System.exit(1);
        }

        return PersonList;
    }
}
