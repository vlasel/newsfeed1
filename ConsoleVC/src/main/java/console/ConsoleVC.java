package console;

import model.dao.exceptions.DaoException;
import model.pojos.Category;
import model.pojos.News;
import model.pojos.User;
import model.pojos.UserInfo;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import services.ServiceUtil;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class ConsoleVC
{
    public static Logger log = Logger.getLogger(ConsoleVC.class);


    public static void main( String[] args )
    {
        ServiceUtil serviceUtil = new ServiceUtil();

        System.out.println(serviceUtil.printHello());


//############################### user ####################################
//        User user = new User();
//        UserInfo userInfo = new UserInfo();

//        user = serviceUtil.getUserByEmail("user1@mail.com");
//        System.out.println("user, email=user1@mail.com: " + user);

//        try {
////            user = serviceUtil.getUserById(1);
////            System.out.println("user by id=1:  " + user);
////            System.out.println("user.getUserInfo: " +user.getUserInfo());
////            System.out.println("user by id=1 roles: " + user.getUserRoleSet());
//            //--------------------------------------------------------------------------------
////            System.out.println("user list: ");
////            List<User> userList = serviceUtil.getUserList();
////            for (User u : userList) {
////                System.out.println("userList.user: " +u);
////                System.out.println("userList.user roles: " +serviceUtil.getUserRoleSetByUser(u));
////            }
//            //--------------------------------------------------------------------------------
////            user = serviceUtil.getUserByEmail("user1@mail.com");
////            System.out.println("getUserByEmail: " +user);
//
////            userInfo = serviceUtil.getUserInfo(2);
////            System.out.println("getUserInfo(1): "+userInfo);
//
////            System.out.println("user.getUserInfo: " +user.getUserInfo());
//
//        }
//        catch (DaoException e) {
//            log.info("DaoException: " +e);
//        }


        //############################### Categories ####################################
//        Category category = new Category();

//        try {
//            //--------------------------------------------------------------------------------
////            for (Category cat : serviceUtil.getCategoryList()) {
////                System.out.println("categoryList.category: " +cat);
//////                System.out.println("categoryList.category newsList: " +serviceUtil.getNewsListByCat(cat));
////                System.out.println("categoryList.category newsList: " +cat.getNewsList());
////            }
//
////            System.out.println("Category by id=1:"+ serviceUtil.getCategory(1));
////            System.out.println("category.newList: "+serviceUtil.getCategory(1).getNewsList());
////            System.out.println("getNewsByCat(category): "+ serviceUtil.getNewsListByCat(category));
//
//            List<Category> categoryList = new ArrayList<Category>();
//            List<News> newsList = new ArrayList<News>();
//
////            categoryList = serviceUtil.getCategoryList();
////            Integer id = Integer.parseInt("1");
////            for(Category cat : categoryList) {
////                if( cat.getId().equals(id) ) {
////                    newsList = serviceUtil.getNewsListByCat(cat);
//////                    newsList = cat.getNewsList();
//////                    Hibernate.initialize(newsList);
////                }
////            }
//
//        }
//        catch (DaoException e) {
//            log.info("DaoException: " +e);
//        }




        //############################### News ####################################

//        News news = new News();
//
//        try {
////            System.out.println("Category by id=1:");
//
////            List<News> newsList = serviceUtil.getNewsList();
////            for (News n : newsList) {
////                System.out.println("newsList.news: " +n);
////            }
//
//            News news = serviceUtil.getNews(1);
//            System.out.println("serviceUtil.getNews(1): "+news);
//        }
//        catch (DaoException e) {
//            log.info("DaoException: " +e);
//        }




        //########################### pageData ##################################


        List<Category> categoryList = new ArrayList<Category>();
        List<News> newsList = new ArrayList<News>();
        Integer newsListCount = null;
        Map pageData = null;
        Map<String,String> ordersMap = new HashMap<>();
        ordersMap.put("name","desc");

        try {
            pageData = serviceUtil.getPageData_ViewByCategory(ordersMap, 1,3 ,1);
        }
        catch (DaoException e) {
            log.info("DaoException: " +e);
        }

        if(pageData != null) {
            categoryList = (List<Category>) pageData.get("categoryList");
            newsList = (List<News>) pageData.get("newsList");
            newsListCount = (Integer) pageData.get("newsListCount");
        }

        System.out.println("Category List: " +categoryList);
        System.out.println("NewsList by category: " +newsList);
        System.out.println("NewsList by category Count:" + newsListCount);





    //#################### program exit ############################################
        System.exit(0);
    }

}


