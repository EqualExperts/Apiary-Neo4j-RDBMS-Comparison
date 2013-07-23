package com.ee.apiary.sql.hibernate.entities;


import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: anuj
 * Date: 26/6/13
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="person")

public class Person {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="level")
    private Integer level;

    public Person(String name, Integer level) {
        this.name = name;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}
