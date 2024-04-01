package bank.configuration;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfiguration {

    private static SessionFactory sessionFactory;

    private HibernateConfiguration(){}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (Exception e) {
                System.out.println("Error " + e);
            }
        }

        return sessionFactory;
    }

}
