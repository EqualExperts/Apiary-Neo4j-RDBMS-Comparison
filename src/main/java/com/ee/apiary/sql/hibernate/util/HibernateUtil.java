package com.ee.apiary.sql.hibernate.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: anuj
 * Date: 26/6/13
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
        	// for MySQL
        	//sessionFactory = new AnnotationConfiguration().configure("hibernate-mysql.cfg.xml").buildSessionFactory();
        	
        	//for SQL server
        	sessionFactory = new AnnotationConfiguration().configure("hibernate-mssql.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            // Log exception!
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
