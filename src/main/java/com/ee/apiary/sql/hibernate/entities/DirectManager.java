package com.ee.apiary.sql.hibernate.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: anuj
 * Date: 27/6/13
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="person_direct_reportee")
public class DirectManager {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name="directly_manages")
    private Person directlyManagedPerson;

    public DirectManager(Person person, Person directlyManagedPerson) {
        this.person = person;
        this.directlyManagedPerson = directlyManagedPerson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getDirectlyManagedPerson() {
        return directlyManagedPerson;
    }

    public void setDirectlyManagedPerson(Person directlyManagedPerson) {
        this.directlyManagedPerson = directlyManagedPerson;
    }
}
