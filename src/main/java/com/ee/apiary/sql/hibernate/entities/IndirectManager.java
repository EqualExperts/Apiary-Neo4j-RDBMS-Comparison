package com.ee.apiary.sql.hibernate.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: anuj
 * Date: 27/6/13
 * Time: 2:22 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="person_indirect_reportee")
public class IndirectManager {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name="indirectly_manages")
    private Person indirectlyManagedPerson;

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

    public Person getIndirectlyManagedPerson() {
        return indirectlyManagedPerson;
    }

    public void setIndirectlyManagedPerson(Person indirectlyManagedPerson) {
        this.indirectlyManagedPerson = indirectlyManagedPerson;
    }
}
