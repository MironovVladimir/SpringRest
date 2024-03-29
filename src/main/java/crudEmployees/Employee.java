package crudEmployees;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {
    String name;
    String surname;
    String date;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    Employee(String name, String surname, String date, long id) {
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.id = id;
    }

    public Employee() {
    }
}
