package com.ee.apiary.sql.core;

import com.ee.apiary.sql.hibernate.entities.DirectManager;
import com.ee.apiary.sql.hibernate.entities.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 * Created with IntelliJ IDEA.
 * User: anuj
 * Date: 28/6/13
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class SqlOrgHierarchy {

    private SortedMap<Integer, OrgLevelData> dataAtLevel = null;
    // private int directlyManagesLimit = 1;   //default value

    public List<DirectManager> createDirectManagerRelationships(SortedMap<Integer, OrgLevelData> levelWithPeople) {
        List<DirectManager> directManagers = new ArrayList<>();
        List<Integer> sortedLevels = new ArrayList<>(levelWithPeople.keySet());

        for (int i = 1; i <= levelWithPeople.size() - 1; i++) {
            List<Person> upperLevelPeople = levelWithPeople.get(sortedLevels.get(0)).getPeople();
            List<Person> lowerLevelPeople = new ArrayList<>(levelWithPeople.get(sortedLevels.get(1)).getPeople());
            int directlyManagesLimit = levelWithPeople.get(sortedLevels.get(0)).getDirectlyManagesLimit();
            sortedLevels.remove(0);

            for (Person upperLevelPerson : upperLevelPeople) {

                for (int j = 0; j < directlyManagesLimit; j++) {
                    if (lowerLevelPeople.size() == 0) {
                        break;
                    }
                    directManagers.add(new DirectManager(upperLevelPerson, lowerLevelPeople.get(0)));
                    lowerLevelPeople.remove(0);
                }
            }
        }
        return directManagers;
    }

    public List<DirectManager> tempCreateDirectManagerRelationships(SortedMap<Integer, OrgLevelData> levelWithPeople) {
        List<DirectManager> directManagers = new ArrayList<>();
        List<Integer> sortedLevels = new ArrayList<>(levelWithPeople.keySet());

        for (int i = 1; i <= levelWithPeople.size() - 1; i++) {
            List<Person> upperLevelPeople = levelWithPeople.get(sortedLevels.get(0)).getPeople();
            List<Person> lowerLevelPeople = new ArrayList<>(levelWithPeople.get(sortedLevels.get(1)).getPeople());
            int directlyManagesLimit = levelWithPeople.get(sortedLevels.get(0)).getDirectlyManagesLimit();
            sortedLevels.remove(0);

            for (Person upperLevelPerson : upperLevelPeople) {

                for (int j = 0; j < directlyManagesLimit; j++) {
                    if (lowerLevelPeople.size() == 0) {
                        break;
                    }
                    directManagers.add(new DirectManager(upperLevelPerson, lowerLevelPeople.get(0)));
                    lowerLevelPeople.remove(0);
                }
            }
        }
        return directManagers;
    }
    
    public static class Builder {
        private SortedMap<Integer, OrgLevelData> dataAtLevel;

        public Builder(SortedMap dataAtLevel) {
            if (dataAtLevel == null || dataAtLevel.isEmpty()) {
                throw new IllegalArgumentException("Organization data cannot be empty");
            }
            this.dataAtLevel = dataAtLevel;
        }

        public SqlOrgHierarchy build() {
            return new SqlOrgHierarchy(this);
        }
    }

    private SqlOrgHierarchy(Builder builder) {
        dataAtLevel = builder.dataAtLevel;
    }

    public SortedMap<Integer, OrgLevelData> toLevelWithPersonObjs(SortedMap<Integer, OrgLevelData> orgDataAtLevel) {
        for (SortedMap.Entry<Integer, OrgLevelData> entry : orgDataAtLevel.entrySet()) {
            List<Person> people = new ArrayList<>();
            for (String personName : entry.getValue().getPersonNames()) {
                Person person = new Person(personName,entry.getKey());
                people.add(person);
            }
            entry.getValue().setPeople(people);
        }
        return orgDataAtLevel;
    }


}
