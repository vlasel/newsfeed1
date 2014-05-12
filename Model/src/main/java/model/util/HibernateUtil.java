package model.util;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Locale;

public class HibernateUtil {

    private static Logger log = Logger.getLogger(HibernateUtil.class);
    private SessionFactory sessionFactory = null;
//    private static final ThreadLocal<Session> SessionInThreadLocal = new ThreadLocal<Session>();
    private static HibernateUtil instance;

    private HibernateUtil() {
        Locale.setDefault(Locale.ENGLISH);
        try {
            if (sessionFactory == null) {

               //___xml___
                Configuration conf = new Configuration();
//                conf.setNamingStrategy(new DefaultNamingStrategy());
                conf.configure();
                //for version 4.2.5
//                ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(conf.getProperties()).buildServiceRegistry();
                //for version 4.3.5
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
                sessionFactory = conf.buildSessionFactory(serviceRegistry);

                log.info("SessionFactory created; SessionFactory = " + sessionFactory);
            }
        } catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed." + ex);
            System.exit(0);
        }
    }


    // this method allows not to use synchronized any time as call getInstance;
    public static HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }


    public Session getSession() {
        Session session = null;
        /*session = sessionFactory.getCurrentSession();
        if (session == null) {
            session = sessionFactory.openSession();
        }*/
        try {
            session = sessionFactory.getCurrentSession();
            log.info("HibernateUtil -> .sessionFactory.getCurrentSession()");
        } catch (HibernateException he) {
            session = sessionFactory.openSession();
            log.info("HibernateUtil -> sessionFactory.openSession()");
        }
        return session;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }


}
