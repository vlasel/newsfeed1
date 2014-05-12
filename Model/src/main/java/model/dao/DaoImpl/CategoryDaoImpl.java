package model.dao.DaoImpl;


import model.dao.DaoAbstract.BaseDaoAbstract;
import model.pojos.Category;
import model.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

public class CategoryDaoImpl extends BaseDaoAbstract<Category> {

    Logger log = Logger.getLogger(CategoryDaoImpl.class);
    private HibernateUtil hibernateUtil = HibernateUtil.getInstance();
    Transaction transaction = null;

    private static CategoryDaoImpl categoryDaoImpl = null;
    private CategoryDaoImpl(){
    }

    public static synchronized CategoryDaoImpl getCategoryDaoImpl() {
        if (categoryDaoImpl == null) {
            categoryDaoImpl = new CategoryDaoImpl();
        }
        return categoryDaoImpl;
    }

    //################################# MEthods #########################################









}

