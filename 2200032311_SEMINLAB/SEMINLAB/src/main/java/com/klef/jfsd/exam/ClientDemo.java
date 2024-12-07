package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class ClientDemo {
  public static void main(String[] args) {
    Configuration cfg = new Configuration();
    cfg.configure("hibernate.cfg.xml");

    SessionFactory sessionFactory = cfg.buildSessionFactory();
    Session session = sessionFactory.openSession();

    Transaction transaction = session.beginTransaction();

    try {
      // Using HQL to update Name and Location based on Department ID
      String hql = "UPDATE Department SET name = ?1, location = ?2 WHERE id = ?3";
      @SuppressWarnings({ "deprecation", "rawtypes" })
	Query query = session.createQuery(hql);

      // Set positional parameters
      query.setParameter(1, "Updated Department Name");
      query.setParameter(2, "Updated Location");
      query.setParameter(3, 1); // Example Department ID

      int result = query.executeUpdate();

      transaction.commit();

      if (result > 0) {
        System.out.println("Department details updated successfully.");
      } else {
        System.out.println("No department found with the given ID.");
      }
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    } finally {
      session.close();
      sessionFactory.close();
    }
  }
}
