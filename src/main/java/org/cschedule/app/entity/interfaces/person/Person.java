package org.cschedule.app.entity.interfaces.person;

/**
 * Created by mac on 09.02.17.
 */
public interface Person {
    String getFname();

    String getSurname();

    String getPatronymic();

    String getType();

    String getEmail();

    String getPassword();

    String[] getSubscriptions();

    void setSubscriptions(String[] subscriptions);

    void print();
}
