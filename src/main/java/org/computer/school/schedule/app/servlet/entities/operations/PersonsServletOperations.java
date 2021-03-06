package org.computer.school.schedule.app.servlet.entities.operations;

import org.computer.school.schedule.app.datastorage.db.sql.SQLReadEntity;
import org.computer.school.schedule.app.datastorage.db.sql.person.reading.PersonContext;
import org.computer.school.schedule.app.datastorage.db.sql.person.reading.ReadPersonByFSP;
import org.computer.school.schedule.app.datastorage.db.sql.person.updating.DeletePerson;
import org.computer.school.schedule.app.datastorage.db.sql.person.updating.InsertPerson;
import org.computer.school.schedule.app.entity.interfaces.person.Person;
import org.computer.school.schedule.app.entity.pojo.person.PersonPOJO;
import org.computer.school.schedule.app.servlet.WebPageMessage;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 22.02.17.
 */
public class PersonsServletOperations implements ServletEntityOperations {
    private String[] pathTokens;
    private Map<String, String[]> pathParams;
    private Connection databaseConnection;
    private WebPageMessage m;

    public PersonsServletOperations(Connection databaseConnection,
                                    HttpServletResponse resp,
                                    Map<String, String[]> pathParams,
                                    String[] pathTokens) {
        this.databaseConnection = databaseConnection;
        this.pathTokens = pathTokens;
        this.pathParams = pathParams;

        m = new WebPageMessage(resp);
    }

    @Override
    public void read() {
        List<Person> persons;
        SQLReadEntity<List<Person>> readPerson;

        String readingOperation = pathTokens[3];
        switch (readingOperation) {
            case "all": {
                readPerson = new PersonContext(databaseConnection);
                persons = readPerson.executeRead();
                m.jsonMessage(persons);
                break;
            }
            case "person": {
                String fname = pathParams.get("fname")[0];
                String surname = pathParams.get("surname")[0];
                String patronymic = pathParams.get("patronymic")[0];
                
                readPerson = new ReadPersonByFSP(databaseConnection, fname, surname, patronymic);
                persons = readPerson.executeRead();
                m.jsonMessage(persons);
                break;
            }
            default: {
                m.message("No such reading operation!");
            }
        }
    }

    @Override
    public void insert() {
        int subscriptionsCount = (pathParams.size() - 6);
        String[] subscriptions = null;

        try {
            subscriptions = new String[subscriptionsCount];

            if(subscriptionsCount > 0)
                for (Integer i = 1; i <= subscriptionsCount; i++)
                    subscriptions[i - 1] = pathParams.get("subscription" + Integer.toString(i))[0];
        }
        catch (NullPointerException e) {
            m.message("Unknown problem!");
            e.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            m.message("Count of parameters is not enough!");
            ex.printStackTrace();
        }

        String fname = pathParams.get("fname")[0];
        String surname = pathParams.get("surname")[0];
        String patronymic = pathParams.get("patronymic")[0];
        String type = pathParams.get("type")[0];
        String email = pathParams.get("email")[0];
        String password = pathParams.get("password")[0];

        InsertPerson insertPerson = new InsertPerson(databaseConnection, new PersonPOJO(
                                                                         fname,
                                                                         surname,
                                                                         patronymic,
                                                                         type,
                                                                         email,
                                                                         password,
                                                                         subscriptions
                                                                                      )
                                                    );
        Integer updateRowsCount = insertPerson.updateProcessing();
        m.message("{\"Rows inserted\": " + "\"" + updateRowsCount + "\"}");
    }

    @Override
    public void delete() {
        String fname = pathParams.get("fname")[0];
        String surname = pathParams.get("surname")[0];
        String patronymic = pathParams.get("patronymic")[0];

        DeletePerson personDelete = new DeletePerson(databaseConnection, fname, surname, patronymic);
        Integer[] deletedRows = personDelete.updateProcessing();

        /** Report of deleted persons from the database */
        Integer personDeletedCount = 0;
        for (Integer d : deletedRows)
            personDeletedCount += d;

        m.message("{\"Persons deleted\": " + "\"" + personDeletedCount + "\"}");
    }
}
