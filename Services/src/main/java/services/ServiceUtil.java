package services;

import model.dao.DaoImpl.*;
import model.dao.exceptions.DaoException;
import model.dto.UserInfoDTO;
import model.pojos.*;
import model.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionException;

import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: _guest
 * Date: 02.05.14
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */
public class ServiceUtil {

    //############# Singleton ######################
    /*private static ServiceUtil serviceUtil = null;
    private ServiceUtil(){
        HibernateUtil.getInstance();
    }
    public static ServiceUtil getServiceUtil() {
        if(serviceUtil == null) {
            serviceUtil = new ServiceUtil();
        }
        return serviceUtil;
    }*/
    //############## default Constructor ###############
    public ServiceUtil(){
    }
//########################### variables ##############################

    private HibernateUtil hibernateUtil = HibernateUtil.getInstance();
    Logger log = Logger.getLogger(ServiceUtil.class);

    private UserDaoImpl userDao = UserDaoImpl.getUserDaoImpl();
    private UserInfoDaoImpl userInfoDao = UserInfoDaoImpl.getUserDaoImpl();
    private UserRoleDaoImpl userRoleDao = UserRoleDaoImpl.getUserRoleDaoImpl();
    private CategoryDaoImpl categoryDao = CategoryDaoImpl.getCategoryDaoImpl();
    private NewsDaoImpl newsDao = NewsDaoImpl.getNewsDaoImpl();

    /*private UserDaoImpl userDao = null;
    private UserRoleDaoImpl userRoleDao = null;
    private CategoryDaoImpl categoryDao = null;
    private NewsDaoImpl newsDao = null;*/

//########################### methods #############################

    //______________________ test _____________________
    public String printHello() {
        return "Hello world";

    }

//################################# get Page Data #####################################

    //_______________________ get data for page view All (default)__________________

    public Map<String,Object> getPageData_ViewAll(Map<String,String> ordersMap, Integer pageNumber, Integer maxResults) throws DaoException{
        Map<String,Object> result = new HashMap<>();
        Session session = hibernateUtil.getSessionFactory().openSession();

        Integer newsListCount = newsDao.getNewsListCount(session);

        Integer pagesCount = newsListCount/maxResults;
        if(newsListCount % maxResults !=0) pagesCount++;

        Integer firstResult;
        if(pageNumber <= pagesCount) {
            firstResult = (pageNumber-1)*maxResults;
        } else firstResult = 1+pagesCount*(maxResults-1);

        List<Category> categoryList = categoryDao.getList(session);
        List<News> newsListByCategoryId = newsDao.getListFormatted(session, ordersMap, firstResult, maxResults, null);



        result.put("categoryList", categoryList);
        result.put("newsList", newsListByCategoryId);
        result.put("newsListCount", newsListCount);
        result.put("pagesCount", pagesCount);


        if( null!=session && session.isOpen() ) {
            try {
                session.close();
            }catch (SessionException e) {
                log.info("error in session.close(): "+e);
            }
        }
        return result;
    }


    //_______________________ get data for page view by Category __________________

    public Map<String,Object> getPageData_ViewByCategory(Map<String,String> ordersMap,
                                                        Integer pageNumber, Integer maxResults,
                                                        Integer categoryId) throws DaoException {

        Map<String,Object> result = new HashMap<>();
        Session session = hibernateUtil.getSessionFactory().openSession();

        Integer newsListCount = newsDao.getNewsListCount(session);

        Integer pagesCount = newsListCount/maxResults;
        if(newsListCount % maxResults !=0) pagesCount++;

        Integer firstResult;
        if(pageNumber <= pagesCount) {
            firstResult = (pageNumber-1)*maxResults;
        } else firstResult = 1+pagesCount*(maxResults-1);

        List<Category> categoryList = categoryDao.getList(session);

        List<News> newsListByCategoryId = null;
        Integer newsListByCategoryIdCount = null;
        for(Category cat : categoryList) {
            if( cat.getId().equals(categoryId) ) {
                newsListByCategoryId = newsDao.getNewsListByCat(session, ordersMap, firstResult, maxResults, cat);
                newsListByCategoryIdCount = newsDao.getNewsListByCatCount(session, cat);
                Hibernate.initialize(newsListByCategoryId);
            }
        }

        result.put("categoryList", categoryList);
        result.put("newsList", newsListByCategoryId);
        result.put("newsListCount", newsListCount);
        result.put("pagesCount", newsListByCategoryIdCount);

        if( null!=session && session.isOpen() ) {
            try {
                session.close();
            }catch (SessionException e) {
                log.info("error in session.close(): "+e);
            }
        }
        return result;
    }







    //_______________________ CATEGORY __________________________

    public Category getCategory(Integer id) throws DaoException{
        Session session = hibernateUtil.getSession();
        return categoryDao.load(session, id);
    }

    public List<Category> getCategoryList() throws DaoException{
        Session session = hibernateUtil.getSession();
        return categoryDao.getList(session);
    }



    //_________________________ NEWS _____________________________

    public News getNews(Integer id) throws DaoException{
        Session session = hibernateUtil.getSession();
        return newsDao.load(session, id);
    }



    //_______________________ USER and ROLES _____________________

    public User getUser(Serializable id) throws DaoException{
        Session session = hibernateUtil.getSession();
        return userDao.load(session, id);
    }

    public UserInfo getUserInfo(Serializable id) throws DaoException {
        Session session = hibernateUtil.getSession();
        return userInfoDao.load(session, id);
    }

    public User getUserByEmail(String email) throws DaoException{
        Session session = hibernateUtil.getSession();
        return userDao.getUserByEmail(session, email);
    }

    public List<User> getUserList() throws DaoException{
        Session session = hibernateUtil.getSession();
        return userDao.getList(session);
    }

    public Set<UserRole> getUserRoleSetByUser(User user) throws DaoException{
        Session session = hibernateUtil.getSession();
        return userRoleDao.getUserRoleSetByUser(session, user);
    }


}
