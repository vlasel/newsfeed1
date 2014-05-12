package model.dao.DaoAbstract.sessionInDao;


import model.dao.DaoInterface.sessionInDao.DAO;
import model.dao.exceptions.DaoException;
import model.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseDaoAbstract<T> implements DAO<T> {

    private static Logger log = Logger.getLogger(BaseDaoAbstract.class);
    Transaction transaction = null;
    private HibernateUtil hibernateUtil = HibernateUtil.getInstance();


    @Override
    public void saveOrUpdate(T obj) throws DaoException {
        log.info("BaseDaoAbstract.SaveOrUpdate by object: " + obj!=null?obj:"null");
        Session session = hibernateUtil.getSession();
        try {


            transaction = session.beginTransaction();
            session.saveOrUpdate(obj);
            transaction.commit();
//            session.getTransaction().commit();
            log.info("BaseDaoAbstract.saveOrUpdate -> COMMIT: " + obj!=null?obj:"null");
        }
        catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                log.info("BaseDaoAbstract.saveOrUpdate -> catch -> ROLLBACK: " + e);
            }
            throw new DaoException(e);
        }
        finally {
            if(null!=session && session.isOpen()) {
                try {
                    session.close();
                }catch (SessionException e) {
                    log.info("error in session.close(): "+e);
                }
            }
        }

    }

    @Override
    public T get(Serializable id) throws DaoException {
        T obj = null;
        log.info("BaseDaoAbstract.Get class by id: " + id);
        Session session = hibernateUtil.getSession();
//        log.info("\n\nget(); session.hash = " +session.hashCode()+ "\n\n");
        try {
            transaction = session.beginTransaction();
            obj = (T) session.get(getPersistentClass(), id);
            transaction.commit();
            log.info("BaseDaoAbstract.get()-> COMMIT: " + ( null==obj?"obj = null":obj.toString() ));
//            System.out.println("get(); id = " + session.getIdentifier(obj));
        }
        catch (HibernateException e) {
            transaction.rollback();
            log.info("BaseDaoAbstract.get()-> ROLLBACK: " + e);
            throw new DaoException(e);
        }
        finally {
            if(null!=session && session.isOpen()) {
                try {
                    session.close();
                }catch (SessionException e) {
                    log.info("error in session.close(): "+e);
                }
            }
        }
        return obj;
    }

    @Override
    public T load(Serializable id) throws DaoException {
        T obj = null;
        log.info("BaseDaoAbstract.Load class by id: " + id);
        Session session = hibernateUtil.getSession();
//        log.info("\n\nload(); session.hash = " +session.hashCode()+ "\n\n");
        try {
            transaction = session.beginTransaction();
            obj = (T) session.load(getPersistentClass(), id);
//            session.load(obj, id);
            transaction.commit();
//            log.info("load() -> COMMIT: " + obj.toString());
            log.info("BaseDaoAbstract.load() -> COMMIT");
        }
        catch (HibernateException e) {
            if(transaction != null && transaction.isActive()) {
                transaction.rollback();
                log.info("BaseDaoAbstract.load() -> ROLLBACK: " + e);
            } else log.info("BaseDaoAbstract.load(): Error in transaction.rollback");
            throw new DaoException(e);
        }
        finally {
            if(null!=session && session.isOpen()) {
                try {
                    session.close();
                }catch (SessionException e) {
                    log.info("error in session.close(): "+e);
                }
            }
        }
        return obj;
    }

    @Override
    public void delete(T obj) throws DaoException {
        log.info("BaseDaoAbstract.Delete class by object: " + obj);
        Session session = hibernateUtil.getSession();
        try {
            transaction = session.beginTransaction();
            session.delete(obj);
            transaction.commit();
            log.info("BaseDaoAbstract.delete() -> COMMIT: " + obj.toString());
        }
        catch (HibernateException e) {
            if(transaction!= null && transaction.isActive()) {
                transaction.rollback();
                log.info("BaseDaoAbstract.delete() -> ROLLBACK: " + e);
            }
            throw new DaoException(e);
        }
        finally {
            if(null!=session && session.isOpen()) {
                try {
                    session.close();
                }catch (SessionException e) {
                    log.info("error in session.close(): "+e);
                }
            }
        }

    }


    public List<T> getList() throws DaoException {
        ArrayList<T> list = new ArrayList<>();
        log.info("BaseDaoAbstract.getList() -> Get list entities;" );
        Session session = hibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            Criteria criteria =  session.createCriteria(getPersistentClass());
            list = (ArrayList<T>) criteria.list();
            transaction.commit();
            log.info("BaseDaoAbstract.getList() -> COMMIT; "/* + list!=null?list.toString():"null"*/);
        }
        catch (HibernateException e) {
            if(transaction!= null && transaction.isActive()) {
                transaction.rollback();
                log.info("BaseDaoAbstract.getList() -> ROLLBACK: "+e);
            }
            throw new DaoException(e);
        }
        finally {
            if(null!=session && session.isOpen()) {
                try {
                    session.close();
                }catch (SessionException e) {
                    log.info("error in session.close(): "+e);
                }
            }
        }
        return list;
    }



    public List<T> getListFormatted(Map<String,String> ordersMap,
                                    Integer firstResult, Integer maxResults,
                                    List<String> fieldsToCriteriaProjection
                                    ) throws DaoException {
        ArrayList<T> list = null;
        log.info("BaseDaoAbstract.Get list.Formatted entities: " );
        Session session = hibernateUtil.getSession();

        /*Order order = null;
        if(orderDirection!=null && !"".equals(orderDirection)) {
            if("acs".equalsIgnoreCase(orderDirection)) order = Order.asc(orderEntityPropertyName).ignoreCase();
            else if("desc".equalsIgnoreCase(orderDirection)) order = Order.desc(orderEntityPropertyName).ignoreCase();
        }*/

        try {
            transaction = session.beginTransaction();
            Criteria criteria;

            criteria  =  session.createCriteria(getPersistentClass());

            if(ordersMap != null && ordersMap.size() > 0) {
                Set<String> keySet = ordersMap.keySet();
                for(String key : keySet) {
                    Order order= null;
                    if("acs".equalsIgnoreCase(ordersMap.get(key))) order = Order.asc(key).ignoreCase();
                    else if("decs".equalsIgnoreCase(ordersMap.get(key))) order = Order.asc(key).ignoreCase();
                    if(order != null) {
                        criteria.addOrder(order);
                    }
                }
            }

            if(firstResult!=null) {
                criteria.setFirstResult(firstResult);
            }
            if(maxResults!=null) {
                criteria.setMaxResults(maxResults);
            }
            if(fieldsToCriteriaProjection != null && fieldsToCriteriaProjection.size() > 0) {
                ProjectionList projectionList = Projections.projectionList();
                for(String projectionProperty : fieldsToCriteriaProjection) {
                    projectionList.add(Projections.property(projectionProperty));
                }
                criteria.setProjection(projectionList);
            }
            list = (ArrayList<T>) criteria.list();
            transaction.commit();
            log.info("BaseDaoAbstract.getListFormatted() -> COMMIT; "/* + list!=null?list.toString():"null"*/);
        }
        catch (HibernateException e) {
            if(transaction!= null && transaction.isActive()) {
                transaction.rollback();
                log.info("BaseDaoAbstract.getListFormatted() -> ROLLBACK: "+e);
            }
            throw new DaoException(e);
        }
        finally {
            if(null!=session && session.isOpen()) {
                try {
                    session.close();
                }catch (SessionException e) {
                    log.info("error in session.close(): "+e);
                }
            }
        }
        return list;
    }

    //##################################### helper methods ##########################################

    private Class getPersistentClass() {
        Class loaderClass = getClass();
        ParameterizedType type = (ParameterizedType) loaderClass.getGenericSuperclass();
        Class genericClass = (Class) type.getActualTypeArguments()[0];
//        return (Class<T>) ( (ParameterizedType) getClass().getGenericSuperclass() ).getActualTypeArguments()[0];
        return genericClass;
    }

}
