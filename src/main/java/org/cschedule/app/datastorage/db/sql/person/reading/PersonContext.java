package org.cschedule.app.datastorage.db.sql.person.reading;

import org.cschedule.app.datastorage.db.DBConnection;
import org.cschedule.app.datastorage.db.sql.SQLReadEntity;
import org.cschedule.app.entity.interfaces.person.Person;
import org.cschedule.app.entity.pojo.person.PersonPOJO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mac on 11.02.17.
 */
public class PersonContext implements SQLReadEntity<List<Person>> {
    private List<Person> result;
    private List<Integer> identifiers = new ArrayList<>();

    private DBConnection DBconnect = new DBConnection();
    private Connection conn = DBconnect.getConnection();

    public PersonContext() {
    }

    PersonContext(List<Person> result, List<Integer> identifiers) {
        this.result = result;
        this.identifiers = identifiers;
    }

    @Override
    public List<Person> executeRead() {
        try (PreparedStatement preparedPerson = conn.prepareStatement(sql())) {
            ResultSet resultPerson = preparedPerson.executeQuery();
            result = extractResult(resultPerson);

            setSubscriptionsTitles(); // setting subscriptions of all persons
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBconnect.close(conn);
        }
        return result;
    }

    void setSubscriptionsTitles() throws SQLException{
        SubscriptionsTitlesByID subsNamesObj =
                new SubscriptionsTitlesByID(identifiers, conn);
        String[][] subsNames = subsNamesObj.executeRead();

        int i = 0;
        for (Person p: result) {
            p.setSubscriptions(subsNames[i]);
            i++;
        }
    }

    @Override
    public String sql() {
        return "select * from users";
    }

    @Override
    public List<Person> extractResult(ResultSet resultSet) {
        List<Person> result = new LinkedList<>();
        Integer id;
        try {
            while (resultSet.next()) {
                id = resultSet.getInt("id"); // get id of each row of the person
                identifiers.add(id);

                String fname = resultSet.getString("fname");
                String surname = resultSet.getString("surname");
                String patronymic = resultSet.getString("patronymic");
                String user_type = resultSet.getString("user_type");
                String password = resultSet.getString("user_password");
                String email = resultSet.getString("email");
                Person person = new PersonPOJO(fname, surname, patronymic, user_type, email, password, null);
                result.add(person);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    List<Integer> getIdentifiers() {
        return identifiers;
    }

    List<Person> getResult() {
        return result;
    }
}
