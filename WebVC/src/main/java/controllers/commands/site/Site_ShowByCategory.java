package controllers.commands.site;

import error.SimpleErrorPage;
import controllers.commands.Command;
import model.dao.exceptions.DaoException;
import model.pojos.Category;
import model.pojos.News;
import org.apache.log4j.Logger;
import services.ServiceUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Site_ShowByCategory extends Command {
    Logger log = Logger.getLogger(Site_ShowByCategory.class);




    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        ServiceUtil serviceUtil = new ServiceUtil();

        final Integer MAX_RESULTS_DEFAULT = 3;
        final Integer PAGE_NUMBER_DEFAULT = 1;

        String orderDir = request.getParameter("orderDir");
        String orderField = request.getParameter("orderField");

        Integer maxResults;
        String maxResultsParam = request.getParameter("maxResults");
        if (maxResultsParam != null && !maxResultsParam.equals("")) {
            maxResults = Integer.parseInt(maxResultsParam);
            if (maxResults <= 0) maxResults = MAX_RESULTS_DEFAULT;
        } else maxResults = MAX_RESULTS_DEFAULT;

        Integer pageNumber;
        String pageNumberParam = request.getParameter("pageNumber");
        if (pageNumberParam != null && pageNumberParam != "") {
            pageNumber = Integer.parseInt(pageNumberParam);
            if (pageNumber <= 0) pageNumber = PAGE_NUMBER_DEFAULT;
        } else pageNumber = PAGE_NUMBER_DEFAULT;

        Integer id = Integer.parseInt(request.getParameter("id"));

        Map<String,String> ordersMap = new HashMap<>();
        ordersMap.put(orderField,orderDir);

        Map pageData = null;
        try {
            pageData = serviceUtil.getPageData_ViewByCategory(ordersMap, pageNumber, maxResults,id);
        }
        catch (DaoException e) {
            new SimpleErrorPage(request,response,"error: Site_ShowByCategory -> serviceUtil. DaoException: \n" +e);
        }


        List<Category> categoryList = (List<Category>) pageData.get("categoryList");
        List<News> newsList = (List<News>) pageData.get("newsList");
        Integer pagesCount = (Integer) pageData.get("pagesCount");
        if (pageNumber > pagesCount) pageNumber = pagesCount;
        Integer newsListCount = (Integer) pageData.get("newsListCount");


        request.setAttribute("categoryList", categoryList);
        request.setAttribute("newsList", newsList);

        request.setAttribute("orderDir", orderDir);
        request.setAttribute("orderField", orderField);
        request.setAttribute("maxResults", maxResults);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("pagesCount", pagesCount);
        request.setAttribute("newsListCount", newsListCount);

        request.setAttribute("viewby",request.getParameter("viewby"));
        request.setAttribute("id",request.getParameter("id"));





        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/siteMain.jsp");

        try {
            dispatcher.forward(request, response);
        }
        catch (ServletException e) {
            new SimpleErrorPage(request,response,"Site_ShowByCategory -> ServletException: \n" + e);
        }
        catch (IOException e) {
            new SimpleErrorPage(request,response,"Site_ShowByCategory -> IOException: \n" + e);
        }
        catch (IllegalStateException e) {
//            new SimpleErrorPage(request,response,"Site_ShowByCategory -> IllegalStateException: \n" + e.getStackTrace());
            log.info(e);
        }


    }

}
