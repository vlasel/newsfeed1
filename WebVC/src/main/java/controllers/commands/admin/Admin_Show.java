package controllers.commands.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.commands.Command;
import model.dao.jdbc.NewsFeedDAO;
import model.dao.jdbc.data.Category;
import model.dto.MenuSection;
import model.dao.jdbc.data.News;

public class Admin_Show extends Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		NewsFeedDAO dao = NewsFeedDAO.getNewsFeedDAO();
		
		Category catData;
		News news;
		
		MenuSection menuSection;
		List<MenuSection> menu = new ArrayList<MenuSection>();
		
		List<Category> listCategories = dao.getCategoryList();
		Iterator<Category> catIterator = listCategories.iterator();
		
		List<News> listNews;
		Iterator<News> newsIterator;
		
		
		while(catIterator.hasNext())
		{
			catData = catIterator.next();
			menuSection = new MenuSection();
			
			menuSection.setId(catData.getId());
			menuSection.setValue(catData.getName());
			
			listNews = dao.getNewsByCat(catData.getId());
			newsIterator = listNews.iterator();
			
			while(newsIterator.hasNext())
			{
				news = newsIterator.next();
				menuSection.addSubsection(news.getId(), news.getName());
			}
			menu.add(menuSection);
		}
		
		request.setAttribute("menu", menu);
		
		
		
		
		
		
		String viewby = request.getParameter("viewby");
		if (viewby != null) {
            if ( viewby.equals("category")) {
                listNews = dao.getNewsByCat(request.getParameter("id"));
                request.setAttribute("listNews", listNews);
            }
        }
        else {
            listNews = dao.getNewsList();
            request.setAttribute("listNews", listNews);
        }

        if (viewby == null) {
            listNews = dao.getNewsList();
            request.setAttribute("listNews", listNews);
        }
        else if ( viewby.equals("category")) {
                listNews = dao.getNewsByCat(request.getParameter("id"));
                request.setAttribute("listNews", listNews);
        }
		
		
		
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/adminMain.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
