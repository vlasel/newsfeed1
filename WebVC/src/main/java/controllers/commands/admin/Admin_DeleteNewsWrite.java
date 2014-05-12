package controllers.commands.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.commands.Command;
import model.dao.jdbc.NewsFeedDAO;

public class Admin_DeleteNewsWrite extends Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		NewsFeedDAO dao = NewsFeedDAO.getNewsFeedDAO();
		
		String id = request.getParameter("id");
		
		dao.deleteNews(id);
		dao.deleteNewsByCat(id);
		
		try {
			response.sendRedirect("AdminController");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
