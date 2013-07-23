package com.ee.apiary.sql.core;

import com.ee.apiary.sql.hibernate.entities.DirectManager;
import com.ee.apiary.sql.hibernate.entities.Person;
import com.ee.apiary.sql.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;

/**
 * Created with IntelliJ IDEA.
 * User: anuj
 * Date: 28/6/13
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class SqlOrgHierarchyDAO {


    public static void saveOrgData(SortedMap<Integer, OrgLevelData> levelWithPeople, List<DirectManager> directManagers) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Iterator<OrgLevelData> it = levelWithPeople.values().iterator();
            while (it.hasNext()) {
              int i = 0;
            	for (Person person : it.next().getPeople()) {
                    session.save(person);
               // System.out.println("Saving person : " + person.getName());
                    if(i % 40 == 0){ //flush a batch of inserts and release memory
                	session.flush();
                	session.clear();
                }
            	}
            }
            int i = 0;
            for (DirectManager directManager : directManagers) {
                session.save(directManager);
               // System.out.println("Saving direct manager : " + directManager.getPerson().getName());
                if(i % 40 == 0){ //flush a batch of inserts and release memory
                	session.flush();
                	session.clear();
                }
            }
            tx.commit();
        } catch (Exception e) {
            System.err.println("Error saving. Rolling back transaction" + e.getMessage());
            tx.rollback();
            throw new RuntimeException(e.getMessage());

        } finally {
            session.close();
        }


    }
}