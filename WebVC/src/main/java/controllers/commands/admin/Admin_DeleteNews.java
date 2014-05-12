package controllers.commands.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.commands.Command;
import model.dao.jdbc.NewsFeedDAO;
import model.dto.MenuSection;
import model.dao.jdbc.data.News;

public class Admin_DeleteNews extends Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		NewsFeedDAO dao = NewsFeedDAO.getNewsFeedDAO();
		
		String id = request.getParameter("id");
		MenuSection menuSection = new MenuSection();
		
		menuSection.setId(id);
		menuSection.setValue(dao.getNews(id).getName());
		
		for(News subPage: dao.getNewsByCat(id)) {
			menuSection.addSubsection(subPage.getId(), subPage.getName());
		}
		
		
		request.setAttribute("menuSection", menuSection);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/adminDelete.jsp");
		
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
}
