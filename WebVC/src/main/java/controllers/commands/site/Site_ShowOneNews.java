package controllers.commands.site;

import error.SimpleErrorPage;
import controllers.commands.Command;
import model.dao.exceptions.DaoException;
import model.dto.NewsInfoDTO;
import model.pojos.Category;
import model.pojos.News;
import services.ServiceUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Site_ShowOneNews extends Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
        ServiceUtil serviceUtil = new ServiceUtil();

		List<News> newsInfoDTOList = new ArrayList<>();
        List<Category> categoryList = new ArrayList<Category>();
        News news = new News();
        Integer id = Integer.parseInt(request.getParameter("id"));
        try {
            categoryList = serviceUtil.getCategoryList();
            news = serviceUtil.getNews(id);
        }
        catch (DaoException e) {
            new SimpleErrorPage(request,response,"error: serviceUtil.getCategoryList(). DaoException: " +e);
        }

		request.setAttribute("categoryList", categoryList);
		request.setAttribute("news", news);

		
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/siteMain.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
            /*SimpleErrorPage errorPage = new SimpleErrorPage();
            request.setAttribute("errorMsg","error; ServletException: " +e);
            errorPage.execute(request, response);*/
            new SimpleErrorPage(request,response,"error; ServletException: " +e);
		} catch (IOException e) {
           /* SimpleErrorPage errorPage = new SimpleErrorPage();
            request.setAttribute("errorMsg","error; ServletException: " +e);
            errorPage.execute(request, response);*/
            new SimpleErrorPage(request,response,"error; ServletException: " +e);
		}


	}

}
