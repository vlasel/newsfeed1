package model.dao.jdbc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.*;

//import org.apache.log4j.Logger;
//import model.util.LoggerMy;

import model.dao.jdbc.data.Category;
import model.dao.jdbc.data.News;
import model.dao.jdbc.data.User;

public class NewsFeedDAO {
	
	private Connection connection;
    private String dbURL;			
    private String dbLogin;			
    private String dbPassword;		
    private String dbDriverName;	
	private final String dbPropertiesFileName = "NewsFeedDAO.properties";


//	Logger log;
    
	//############################## Constructor ####################################################
	private NewsFeedDAO()
	{

//		log = LoggerMy.getLogger(this.getClass());
		//----- get db model.model.dao.config ---------------
		getDbConfig(dbPropertiesFileName);

		//------- get connection to mysql --------------------------------------------		
		try {
			Class.forName(dbDriverName);
		} 
		catch (ClassNotFoundException cnfe) {
//            log.error("Error loading driver: " +cnfe.getStackTrace());
            cnfe.printStackTrace();
        } // use logger log4j or java.model.util.Logging
		
		try {
			Locale.setDefault(Locale.ENGLISH);
			connection = DriverManager.getConnection(dbURL,dbLogin,dbPassword);
			Locale.setDefault(Locale.getDefault());
		} 
		catch (SQLException e) {
//            log.error("SQL Exception: "+e.getStackTrace());
            e.printStackTrace();
		}
	}
	
	//############################### Method-fabric Singleton ####################################
	private static NewsFeedDAO instance;
	public static NewsFeedDAO getNewsFeedDAO()
	{
		if(instance == null)
			instance = new NewsFeedDAO();
		return instance;
	}
	
	//############## get model.model.dao.config for connection to mysql from <classPath>\model.model.dao.config\NewsFeedDAO.properties ##############
	private void getDbConfig (String propsFileName) {
//		String propsFilePath = "/model.model.dao/" + propsFileName;
//		String propsFilePath = propsFileName;
//		InputStream inStream = null;
		Properties props = new Properties();
		try {
//			inStream = this.getClass().getResourceAsStream(propsFileName);
//			inStream = this.getClass().getResourceAsStream(propsFilePath);
            props.load( this.getClass().getResourceAsStream(propsFileName) );
//            props.load( inStream );
//            inStream.close();
		}
		catch (FileNotFoundException e) { e.printStackTrace(); } 
		catch (IOException e) { e.printStackTrace(); }
		this.dbURL = props.getProperty("dbURL");
		this.dbLogin = props.getProperty("dbLogin");
		this.dbPassword = props.getProperty("dbPassword");
		this.dbDriverName = props.getProperty("dbDriverName");
        /*this.dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
		this.dbLogin = "newsfeed";
		this.dbPassword = "newsfeed";
		this.dbDriverName = "oracle.jdbc.OracleDriver";*/
	}
		
			
	/*################################################################################################################
	  ########################################### Class Methods ######################################################	
	  ################################################################################################################*/

	//#################################### work with news ###########################################
	
	public News getNews(String id)
	{
		News news = null;
		PreparedStatement pStatement=null;
		String query = "select * from T_NEWS where F_ID=?";
		
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, Integer.parseInt(id));
			ResultSet result = pStatement.executeQuery();
			if(result.next())
			{
				news = new News();
				news.setId(result.getString("F_ID"));
				news.setIdcat(result.getString("F_IDCAT"));
				news.setName(result.getString("F_NAME"));
				news.setAuthor(result.getString("F_AUTHOR"));
				news.setDate( result.getString("F_DATE") );
				news.setTime( result.getString("F_TIME") );
				news.setAnnotation(result.getString("F_ANNOTATION"));
				news.setMaintext(result.getString("F_MAINTEXT"));
			}
			else 
				return null;
		} catch (SQLException e) { e.printStackTrace(); }
		return news;
	}
	
	
	public List<News> getNewsList()
	{
		List<News> newsList = new ArrayList<News>();
		PreparedStatement pStatement=null;
		String query = "select * from T_NEWS";
		try {
			pStatement = connection.prepareStatement(query);
			ResultSet result = pStatement.executeQuery();
			while(result.next())
			{
				News data = new News();
				data.setId(result.getString("F_ID"));
				data.setIdcat(result.getString("F_IDCAT"));
				data.setName(result.getString("F_NAME"));
				data.setAuthor(result.getString("F_AUTHOR"));
				data.setDate( result.getString("F_DATE") );
				data.setTime( result.getString("F_TIME") );
				data.setAnnotation(result.getString("F_ANNOTATION"));
				data.setMaintext(result.getString("F_MAINTEXT"));
				
				newsList.add(data);
			}
		}catch (SQLException e) { e.printStackTrace(); }
		return newsList;
	}
	
	
	public List<News> getNewsByCat(String idcat)
	{
		List<News> newsList = new ArrayList<News>();
		PreparedStatement pStatement=null;
		String query = "select * from T_NEWS where F_IDCAT=?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, Integer.parseInt(idcat));
			ResultSet result = pStatement.executeQuery();
			while(result.next())
			{
				News data = new News();
				data.setId(result.getString("F_ID"));
				data.setIdcat(result.getString("F_IDCAT"));
				data.setName(result.getString("F_NAME"));
				data.setAuthor(result.getString("F_AUTHOR"));
				data.setDate( result.getString("F_DATE") );
				data.setTime( result.getString("F_TIME") );
				data.setAnnotation(result.getString("F_ANNOTATION"));
				data.setMaintext(result.getString("F_MAINTEXT"));
				
				newsList.add(data);
			}
		}catch (SQLException e) { e.printStackTrace(); }
		return newsList;
	}
	
	
	public List<News> getNewsByAuthor(String author)
	{
		List<News> newsList = new ArrayList<News>();
		PreparedStatement pStatement=null;
		String query = "select * from T_NEWS where F_AUTHOR=?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, author);
			ResultSet result = pStatement.executeQuery();
			while(result.next())
			{
				News data = new News();
				data.setId(result.getString("F_ID"));
				data.setIdcat(result.getString("F_IDCAT"));
				data.setName(result.getString("F_NAME"));
				data.setAuthor(result.getString("F_AUTHOR"));
				data.setDate( result.getString("F_DATE") );
				data.setTime( result.getString("F_TIME") );
				data.setAnnotation(result.getString("F_ANNOTATION"));
				data.setMaintext(result.getString("F_MAINTEXT"));
				
				newsList.add(data);
			}
		}catch (SQLException e) { e.printStackTrace(); }
		return newsList;
	}
	
	
	public List<News> getNewsByDate(String date)
	{
		List<News> newsList = new ArrayList<News>();
		PreparedStatement pStatement=null;
		String query = "select * from T_NEWS where F_DATE=?";
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, date);
			ResultSet result = pStatement.executeQuery();
			while(result.next())
			{
				News data = new News();
				data.setId(result.getString("F_ID"));
				data.setIdcat(result.getString("F_IDCAT"));
				data.setName(result.getString("F_NAME"));
				data.setAuthor(result.getString("F_AUTHOR"));
				data.setDate( result.getString("F_DATE") );
				data.setTime( result.getString("F_TIME") );
				data.setAnnotation(result.getString("F_ANNOTATION"));
				data.setMaintext(result.getString("F_MAINTEXT"));
				
				newsList.add(data);
			}
		}catch (SQLException e) { e.printStackTrace(); }
		return newsList;
	}
	
	
	public int addNews(News news)
	{
		PreparedStatement pStatement=null;
		String query = "INSERT "
				+ "INTO T_NEWS (F_IDCAT, F_NAME, F_AUTHOR, F_DATE, F_TIME, F_ANNOTATION, F_MAINTEXT)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		int result = 0;
		
		try {
			pStatement = connection.prepareStatement(query);
			
			pStatement.setInt(1, Integer.parseInt(news.getIdcat()));
			pStatement.setString(2, news.getName());
			pStatement.setString(3, news.getAuthor());
			pStatement.setString(4, DateFormat.getDateInstance(2).format(new Date()) );
			pStatement.setString(5, DateFormat.getTimeInstance(3).format(new Date()) );
			pStatement.setString(6, news.getAnnotation());
			pStatement.setString(7, news.getMaintext());
			
			result = pStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
		
		return result;
	}

	
	public int updateNews(News news) {
		PreparedStatement pStatement=null;
		String query = "UPDATE T_NEWS "
				+ "SET F_IDCAT=?, F_NAME=?, F_AUTHOR=?, F_DATE=?, F_TIME=?, F_ANNOTATION=?, F_MAINTEXT=?"
				+ " WHERE F_ID=?";
		int result = 0;
		
		try {
			pStatement = connection.prepareStatement(query);
						
			pStatement.setInt(1, Integer.parseInt(news.getIdcat()) );
			pStatement.setString(2, news.getName());
			pStatement.setString(3, news.getAuthor());
			pStatement.setString(4, DateFormat.getDateInstance(2).format(new Date()) );
			pStatement.setString(5, DateFormat.getTimeInstance(3).format(new Date()) );
			pStatement.setString(6, news.getAnnotation());
			pStatement.setString(7, news.getMaintext());
			pStatement.setInt(8, Integer.parseInt(news.getId()) );
			
			result = pStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
		
		return result;
	}

	public int deleteNews(String id) {
		int result = 0;
		
		PreparedStatement pStatement=null;
		String query = "DELETE FROM T_NEWS WHERE F_ID=?";
		
		try {
			pStatement = connection.prepareStatement(query);
						
			pStatement.setInt(1, Integer.parseInt(id));
			
			result = pStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
		
		return result;
	}

	public int deleteNewsByCat(String idcat) {
		int result = 0;
		
		PreparedStatement pStatement=null;
		String query = "DELETE FROM T_NEWS WHERE F_IDCAT=?";
		
		try {
			pStatement = connection.prepareStatement(query);
						
			pStatement.setInt(1, Integer.parseInt(idcat));
			
			result = pStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
		
		return result;
	}
	
	
	//###################################### work with category ####################################
	
	public Category getCategory(String id)
	{
		Category catData = null;
		PreparedStatement pStatement=null;
		String query = "select * from T_CATEGORY where F_ID=?";
		
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, Integer.parseInt(id));
			ResultSet result = pStatement.executeQuery();
			if(result.next())
			{
				catData = new Category();
				catData.setId(String.valueOf(result.getInt("F_ID")));
				catData.setName(result.getString("F_NAME"));
			}
			else 
				return null;
		} catch (SQLException e) { e.printStackTrace(); }
		return catData;
	}
	
	public List<Category> getCategoryList()
	{
		List<Category> catDataList = new ArrayList<Category>();
		PreparedStatement pStatement=null;
		String query = "select * from T_CATEGORY";
		try {
			pStatement = connection.prepareStatement(query);
			ResultSet result = pStatement.executeQuery();
			while(result.next())
			{
				Category catData = new Category();
				catData.setId(String.valueOf(result.getInt("F_ID")));
				catData.setName(result.getString("F_NAME"));
				
				catDataList.add(catData);
			}
		}catch (SQLException e) { e.printStackTrace(); }
		return catDataList;
	}
	
	public List<String> getCategoryIdList() {
		List<String> idList = new ArrayList<String>();
		PreparedStatement pStatement=null;
		String query = "select id from T_CATEGORY";
		try {
			pStatement = connection.prepareStatement(query);
			ResultSet result = pStatement.executeQuery();
			while(result.next())
			{
				String id = String.valueOf(result.getInt("F_ID"));
				idList.add(id);
			}
		}catch (SQLException e) { e.printStackTrace(); }
		return idList;
	}
	
	public int addCategory(Category catData)
	{
		PreparedStatement pStatement=null;
		String query = "INSERT "
				+ "INTO T_CATEGORY (F_NAME)"
				+ " VALUES (?)";
		int result = 0;
		try {
			pStatement = connection.prepareStatement(query);
			
			pStatement.setString(1, catData.getName());
			
			result = pStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
		return result;
	}

	public int deleteCategory(String id) {
		int result = 0;
		
		PreparedStatement pStatement=null;
		String query = "DELETE FROM T_CATEGORY WHERE F_ID=?";
		
		try {
			pStatement = connection.prepareStatement(query);
						
			pStatement.setInt(1, Integer.parseInt(id));
			
			result = pStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
		
		return result;
	}
	
	
	//##################################### work with users ###########################################
	
	public User getUser(String email)
	{
		User userdata = null;
		PreparedStatement pStatement=null;
		String query = "select * from T_USER where F_EMAIL=?";
		
		try {
			pStatement = connection.prepareStatement(query);
			pStatement.setString(1, email);
			ResultSet result = pStatement.executeQuery();
			if(result.next())
			{
				userdata = new User();
				userdata.setEmail(result.getString("F_EMAIL"));
				userdata.setPass(result.getString("F_PASS"));
				userdata.setName(result.getString("F_NAME"));
				userdata.setSurname(result.getString("F_SURNAME"));
			}
			else 
				return null;
		} catch (SQLException e) { e.printStackTrace(); }
		return userdata;
	}

    public User getUser(int id)
    {
        User userdata = null;
        PreparedStatement pStatement=null;
        String query = "select * from T_USER where F_ID=?";

        try {
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);
            ResultSet result = pStatement.executeQuery();
            if(result.next())
            {
                userdata = new User();
                userdata.setEmail(result.getString("F_EMAIL"));
                userdata.setPass(result.getString("F_PASS"));
                userdata.setName(result.getString("F_NAME"));
                userdata.setSurname(result.getString("F_SURNAME"));
            }
            else
                return null;
        } catch (SQLException e) { e.printStackTrace(); }
        return userdata;
    }
	
	public int addUser(User user)
	{
		PreparedStatement pStatement=null;
		String query = "INSERT "
				+ "INTO T_USER (F_EMAIL, F_PASS, F_NAME, F_SURNAME)"
				+ " VALUES (?, ?, ?, ?)";
		int result = 0;
		try {
			pStatement = connection.prepareStatement(query);
			
			pStatement.setString(1, user.getEmail());
			pStatement.setString(2, user.getPass());
			pStatement.setString(3, user.getName());
			pStatement.setString(4, user.getSurname());
			
			result = pStatement.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
		return result;
	}
	
}

