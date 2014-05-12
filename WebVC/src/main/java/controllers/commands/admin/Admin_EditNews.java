package controllers.commands.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.commands.Command;
import model.dao.jdbc.NewsFeedDAO;
import model.dao.jdbc.data.News;

public class Admin_EditNews extends Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		NewsFeedDAO dao = NewsFeedDAO.getNewsFeedDAO();
		
		News news = dao.getNews(request.getParameter("id"));
		
		request.setAttribute("categoryList", dao.getCategoryList());
		request.setAttribute("newsdata", news);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/adminEdit.jsp");
		
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
}
