package model.dao.DaoImpl;


import model.dao.DaoAbstract.BaseDaoAbstract;
import model.dao.exceptions.DaoException;
import model.pojos.UserRole;
import org.apache.log4j.Logger;
import model.pojos.User;
import model.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserDaoImpl extends BaseDaoAbstract<User> {

    Logger log = Logger.getLogger(UserDaoImpl.class);
    private HibernateUtil hibernateUtil = HibernateUtil.getInstance();
    Transaction transaction = null;

    private static UserDaoImpl userDaoImpl = null;
    private UserDaoImpl(){
    }

    public static synchronized UserDaoImpl getUserDaoImpl() {
        if (userDaoImpl == null) {
            userDaoImpl = new UserDaoImpl();
        }
        return userDaoImpl;
    }

    //################################# MEthods #########################################

    //inherited


    public User getUserByEmail(Session session, String email) throws DaoException{
        User user = null;
//        Session session = hibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("email",email));

            if(criteria.list().size() == 1) {
                user = (User) criteria.list().get(0);
            }
            transaction.commit();
            log.info("UserDaoImpl.getUserByEmail -> COMMIT;");

        }
        catch (HibernateException e){
            if(transaction!=null && transaction.isActive()) {
                transaction.rollback();
                log.info("UserDaoImpl.getUserByEmail -> ROLLBACK;");
            }
            log.info("UserDaoImpl.getUserByEmail -> HibernateException -> throw new DaoException; e:"+e);
            throw new DaoException(e);

        }
        /*finally {
            if(session!=null && session.isOpen()) {
                session.close();
            }
        }*/
        return user;
    }









}

