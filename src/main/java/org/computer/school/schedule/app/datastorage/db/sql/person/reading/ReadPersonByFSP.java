package org.computer.school.schedule.app.datastorage.db.sql.person.reading;

import org.computer.school.schedule.app.datastorage.db.sql.SQLReadEntity;
import org.computer.school.schedule.app.entity.interfaces.person.Person;
import org.computer.school.schedule.app.datastorage.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by mac on 16.02.17.
 */
public class ReadPersonByFSP implements SQLReadEntity<List<Person>> {
    private String fname;
    private String surname;
    private String patronymic;
    private List<Person> result;
    private Connection databaseConnection;

    public ReadPersonByFSP(Connection databaseConnection, String fname, String surname, String patronymic) {
        this.databaseConnection = databaseConnection;
        this.fname = fname;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    @Override
    public List<Person> executeRead() {
        ResultSet resultSet = null;
        return extractResult(resultSet);
    }

    @Override
    public List<Person> extractResult(ResultSet resultSet) {
        ResultSet resultPerson;
        try (PreparedStatement preparedPerson = databaseConnection.prepareStatement(sql())) {
            setParameters(preparedPerson);
            resultPerson = preparedPerson.executeQuery();

            PersonContext personContextExtractResult = new PersonContext(databaseConnection);
            PersonContext personContextSetSubscriptions =
                    new PersonContext(databaseConnection,
                                      personContextExtractResult.extractResult(resultPerson),
                                      personContextExtractResult.getIdentifiers());

            personContextSetSubscriptions.setSubscriptionsTitles();
            result = personContextSetSubscriptions.getResult();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void setParameters(PreparedStatement statement) throws SQLException{
        statement.setString(1, fname);
        statement.setString(2, surname);
        statement.setString(3, patronymic);
    }

    @Override
    public String sql() {
        return  "select * from users " +
                "where fname = ? AND surname = ? AND patronymic = ?";
    }
}
