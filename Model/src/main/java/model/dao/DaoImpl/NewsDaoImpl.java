package model.dao.DaoImpl;


import model.dao.DaoAbstract.BaseDaoAbstract;
import model.dao.exceptions.DaoException;
import model.pojos.Category;
import model.pojos.News;
import model.pojos.User;
import model.pojos.UserRole;
import model.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NewsDaoImpl extends BaseDaoAbstract<News> {

    Logger log = Logger.getLogger(NewsDaoImpl.class);
    private HibernateUtil hibernateUtil = HibernateUtil.getInstance();
    Transaction transaction = null;

    private static NewsDaoImpl newsDaoImpl = null;
    private NewsDaoImpl(){
    }

    public static synchronized NewsDaoImpl getNewsDaoImpl() {
        if (newsDaoImpl == null) {
            newsDaoImpl = new NewsDaoImpl();
        }
        return newsDaoImpl;
    }

    //################################# MEthods #########################################


    public List<News> getNewsListByCat(Session session, Category category) throws DaoException {
        List<News> list = null;

        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(News.class);
            criteria.add(Restrictions.eq("category",category));

            list = criteria.list();

            log.info("NewsDaoImpl.getNewsListByCat; ");
            transaction.commit();
            log.info("NewsDaoImpl.getNewsListByCat -> COMMIT; ");

        }
        catch (HibernateException e) {
            if(transaction!=null && transaction.isActive()) {
                transaction.rollback();
                log.info("NewsDaoImpl.getNewsByCat() -> ROLLBACK: "+e);
            }
            throw new DaoException(e);
        }
        return list;
    }


    public List<News> getNewsListByCat(Session session, Map<String,String> ordersMap,
                                       Integer firstResult, Integer maxResults,
                                       Category category) throws DaoException {
        List<News> list = null;
        Criterion criterion = Restrictions.eq("category",category);

        list = getListFormatted(session, ordersMap, firstResult, maxResults, criterion);

        log.info("NewsDaoImpl.getNewsListByCat; ");

        return list;
    }


    public Integer getNewsListByCatCount(Session session, Category category) throws DaoException {
        Integer result = null;
//        Session session = hibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(News.class);
            criteria.add(Restrictions.eq("category",category));
            criteria.setProjection(Projections.rowCount());

            result = ((Number) criteria.uniqueResult()).intValue();

            transaction.commit();
            log.info("NewsDaoImpl.getNewsListByCatCount -> COMMIT; ");
        }
        catch (HibernateException e) {
            if(transaction!=null && transaction.isActive()) {
                transaction.rollback();
                log.info("NewsDaoImpl.getNewsListByCatCount -> ROLLBACK: "+e);
            }
            throw new DaoException(e);
        }
        return result;
    }



    public Integer getNewsListCount(Session session) throws DaoException {
        Integer result = null;
//        Session session = hibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(News.class);
            criteria.setProjection(Projections.rowCount());

            result = ((Number) criteria.uniqueResult()).intValue();

            transaction.commit();
            log.info("NewsDaoImpl.getNewsListCount -> COMMIT; ");
        }
        catch (HibernateException e) {
            if(transaction!=null && transaction.isActive()) {
                transaction.rollback();
                log.info("NewsDaoImpl.getNewsListCount -> ROLLBACK: "+e);
            }
            throw new DaoException(e);
        }
        return result;
    }

}

