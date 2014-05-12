package model.dao.DaoImpl;


import model.dao.DaoAbstract.BaseDaoAbstract;
import model.dao.exceptions.DaoException;
import model.pojos.User;
import org.apache.log4j.Logger;
import model.pojos.UserRole;
import model.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;

public class UserRoleDaoImpl extends BaseDaoAbstract<UserRole> {

    Logger log = Logger.getLogger(UserRoleDaoImpl.class);
    private HibernateUtil hibernateUtil = HibernateUtil.getInstance();
    Transaction transaction = null;

    private static UserRoleDaoImpl userRoleDaoImpl = null;
    private UserRoleDaoImpl(){
    }

    public static synchronized UserRoleDaoImpl getUserRoleDaoImpl() {
        if (userRoleDaoImpl == null) {
            userRoleDaoImpl = new UserRoleDaoImpl();
        }
        return userRoleDaoImpl;
    }

    //################################# MEthods #########################################

    public Set<UserRole> getUserRoleSetByUser(Session session, User user) throws DaoException {
        Set<UserRole> set = null;
//        Session session = hibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            if (!session.contains(user)) {
                    log.info("UserRoleDaoImpl.getUserRoleSetByUser() -> Another session;");
                    session.load(user, user.getId());
                    log.info("UserRoleDaoImpl.getUserRoleSetByUser() -> session.load(user)");
            }
            set = user.getUserRoleSet();
            log.info("UserRoleDaoImpl.getUserRoleSetByUser() -> user.getUserRoleSet(); ");
            transaction.commit();
            log.info("UserRoleDaoImpl.getUserRoleSetByUser() -> COMMIT; ");

        }
        catch (HibernateException e) {
            if(transaction!= null) {
                transaction.rollback();
                log.info("UserRoleDaoImpl.getUserRoleSetByUser() -> ROLLBACK: "+e);
            }
            throw new DaoException(e);
        }
        /*finally {
            if(session!=null && session.isOpen()) {
                session.close();
            }
        }*/
        return set;
    }


}

