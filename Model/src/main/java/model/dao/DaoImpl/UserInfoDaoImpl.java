package model.dao.DaoImpl;


import model.dao.DaoAbstract.BaseDaoAbstract;
import model.dao.exceptions.DaoException;
import model.pojos.User;
import model.pojos.UserInfo;
import model.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class UserInfoDaoImpl extends BaseDaoAbstract<UserInfo> {

    Logger log = Logger.getLogger(UserInfoDaoImpl.class);
    private HibernateUtil hibernateUtil = HibernateUtil.getInstance();
    Transaction transaction = null;

    private static UserInfoDaoImpl userDaoImpl = null;
    private UserInfoDaoImpl(){
    }

    public static synchronized UserInfoDaoImpl getUserDaoImpl() {
        if (userDaoImpl == null) {
            userDaoImpl = new UserInfoDaoImpl();
        }
        return userDaoImpl;
    }

    //################################# MEthods #########################################

    //inherited












}

