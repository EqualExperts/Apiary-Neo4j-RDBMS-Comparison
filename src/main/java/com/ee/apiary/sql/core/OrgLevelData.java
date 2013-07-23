package com.ee.apiary.sql.core;

import com.ee.apiary.sql.hibernate.entities.Person;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: anuj
 * Date: 1/7/13
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrgLevelData {

    List<String> personNames = null;
    int directlyManagesLimit = 1;  //default
    List<Person> people = null;

    public OrgLevelData(List<String> personNames) {
        if (personNames == null || personNames.isEmpty()) {
            throw new IllegalArgumentException("People data is empty/incorrect.");
        }
        this.personNames = personNames;
    }

    public OrgLevelData(List<String> personNames, int directlyManagesLimit) {
        if (personNames == null || personNames.isEmpty() || directlyManagesLimit <= 0) {
            throw new IllegalArgumentException("People data or directly manages limit is empty/incorrect.");
        }
        this.personNames = personNames;
        this.directlyManagesLimit = directlyManagesLimit;
    }

    public int getDirectlyManagesLimit() {
        return directlyManagesLimit;
    }

    public List<String> getPersonNames() {
        return personNames;
    }

    protected List<Person> getPeople() {
        return people;
    }

    protected void setPeople(List<Person> people) {
        this.people = people;
    }
}
