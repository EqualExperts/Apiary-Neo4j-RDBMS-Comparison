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
    private Person manager;

    @ManyToOne
    @JoinColumn(name="directly_manages")
    private Person reportee;

    public DirectManager(Person manager, Person reportee) {
        this.manager = manager;
        this.reportee = reportee;
    }

    public Long getId() {
        return id;
    }

    @Deprecated
    private void setId(Long id) {
        this.id = id;
    }

    public Person getManager() {
        return manager;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }

    public Person getReportee() {
        return reportee;
    }

    public void setReportee(Person reportee) {
        this.reportee = reportee;
    }
}
