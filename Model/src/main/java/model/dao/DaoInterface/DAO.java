package model.dao.DaoInterface;


import model.dao.exceptions.DaoException;
import org.hibernate.Session;

import java.io.Serializable;

public interface DAO<T> {
    void saveOrUpdate(Session session, T obj) throws DaoException;
    T get(Session session, Serializable id) throws DaoException;
    T load(Session session, Serializable id) throws DaoException;
    void delete(Session session, T obj) throws DaoException;
}
