package model.dao.DaoInterface.sessionInDao;


import model.dao.exceptions.DaoException;

import java.io.Serializable;

public interface DAO<T> {
    void saveOrUpdate(T obj) throws DaoException;
    T get(Serializable id) throws DaoException;
    T load(Serializable id) throws DaoException;
    void delete(T obj) throws DaoException;
}
