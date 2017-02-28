package org.cschedule.app.entity.pojo.person;

import org.cschedule.app.entity.interfaces.person.Person;

/**
 * Created by mac on 09.02.17.
 */
public class PersonPOJO implements Person {
    String fname;
    String surname;
    String patronymic;
    String type;
    String email;
    String password;
    String subscriptions[];

    public PersonPOJO() {}

    public PersonPOJO(String fname, String surname, String patronymic, String type, String email, String password, String[] subscriptions) {
        this.fname = fname;
        this.surname = surname;
        this.patronymic = patronymic;
        this.type = type;
        this.email = email;
        this.password = password;
        this.subscriptions = subscriptions;
    }

    @Override
    public String getFname() {
        return fname;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getPatronymic() {
        return patronymic;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String[] getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(String[] subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public void print() {
            System.out.println("fname " + fname);
            System.out.println("surname: " + surname);
            System.out.println("patronymic: " + patronymic);
            System.out.println("type: " + type);
            System.out.println("email: " + email);
            System.out.println("password: " + password);
            System.out.println("subscriptions:");
            for (String sub : subscriptions)
                 System.out.println(sub);
    }
}
