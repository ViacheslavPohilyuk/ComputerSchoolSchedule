package org.computer.school.schedule.app.datastorage.db.sql.person.updating;

import org.computer.school.schedule.app.datastorage.db.sql.SQLUpdateEntity;
import org.computer.school.schedule.app.entity.interfaces.person.Person;
import org.computer.school.schedule.app.datastorage.db.DBConnection;

import java.sql.*;

/**
 * Created by mac on 10.02.17.
 */
public class InsertPerson implements SQLUpdateEntity<Integer> {
    private String fname;
    private String surname;
    private String patronymic;
    private String type;
    private String email;
    private String password;
    private String subscriptions[];
    private Connection databaseConnection;

    private int key; // id of new person

    public InsertPerson(Connection databaseConnection, Person person) {
        this.databaseConnection = databaseConnection;
        this.fname = person.getFname();
        this.surname = person.getSurname();
        this.patronymic = person.getPatronymic();
        this.type = person.getType();
        this.email = person.getEmail();
        this.password = person.getPassword();
        this.subscriptions = person.getSubscriptions();
    }

    @Override
    public Integer updateProcessing() {
        Integer updateCount = personInsert();
                       subscriptionsInsert();
        return updateCount;
    }

    private Integer personInsert() {
        Integer updateCount = 0;
        try (PreparedStatement preparedPerson = databaseConnection.prepareStatement(sql(),
                                                Statement.RETURN_GENERATED_KEYS))
        {
            setEntityParameters(preparedPerson);
            updateCount = preparedPerson.executeUpdate();

            /** Get id of the inserted person */
            ResultSet generatedKeys = preparedPerson.getGeneratedKeys();
            while (generatedKeys.next())
                key = generatedKeys.getInt(1);
        }
        catch (SQLException e) {
            System.err.println("Personality inserting error!");
            e.printStackTrace();
        }
        return updateCount;
    }

    private void subscriptionsInsert() {
        try(PreparedStatement preparedSubscription = databaseConnection.prepareStatement(sqlSubscriptionsStatement())) {
            if (subscriptions.length > 0) {
                SubscriptionsIDByTitles subID = new SubscriptionsIDByTitles(subscriptions, databaseConnection);
                int[] subsPersonId = subID.executeRead();

                for (int id : subsPersonId) {
                    setSubscriptionsParameters(preparedSubscription, id, key);
                    preparedSubscription.addBatch();
                }
                preparedSubscription.executeBatch();
            }
        }
        catch (BatchUpdateException e) {
            System.err.println("Batch error!");
            e.printStackTrace();
        }
        catch (SQLException ex) {
            System.err.println("Subscriptions inserting error!");
            ex.printStackTrace();
        }
    }

    @Override
    public String sql() {
        return "insert into users(fname, surname, patronymic, user_type, email, user_password)" +
                " VALUES(?,?,?,?,?,?)";
    }

    @Override
    public void setEntityParameters(PreparedStatement statement) {
        try {
            statement.setString(1, fname);
            statement.setString(2, surname);
            statement.setString(3, patronymic);
            statement.setString(4, type);
            statement.setString(5, email);
            statement.setString(6, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String sqlSubscriptionsStatement() {
        return "insert into subscriptions(course_id, user_id)" +
                "VALUES(?,?)";
    }

    private void setSubscriptionsParameters(PreparedStatement statement, int id, int key) throws SQLException {
        statement.setInt(1, id);
        statement.setInt(2, key);
    }
}
