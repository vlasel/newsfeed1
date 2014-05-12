package controllers.commands.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.commands.Command;
import model.dao.jdbc.NewsFeedDAO;
import model.dao.jdbc.data.News;

public class Admin_AddNewsWrite extends Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		News news = new News();
		NewsFeedDAO dao = NewsFeedDAO.getNewsFeedDAO();
		
		news.setIdcat(request.getParameter("idcat"));
		news.setName(request.getParameter("name"));
		news.setAuthor(request.getParameter("author"));
		news.setDate( request.getParameter("date") );
		news.setTime( request.getParameter("time") );
		news.setAnnotation(request.getParameter("annotation"));
		news.setMaintext(request.getParameter("maintext"));
		
		dao.addNews(news);
		
		
		try {
			response.sendRedirect("AdminController");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
