package com.junapp.lms.persistence;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
  
public class HibernateUtil {
  
    private static final SessionFactory sessionFactory = buildSessionFactory();
  
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
  
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     * @return current session or a new one, if none exists
     */
    public static Session getSession() {
    	try {
    		return HibernateUtil.getSessionFactory().getCurrentSession();
    	} catch (HibernateException e) {
    		return HibernateUtil.getSessionFactory().openSession();
    	}
    }
  
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
  
}