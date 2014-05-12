package controllers;


import java.io.IOException;
import java.lang.String;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.commands.Command;
import controllers.commands.site.Site_ShowAll;
import controllers.commands.site.Site_ShowByCategory;
import controllers.commands.site.Site_ShowOneNews;

public class SiteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SiteController() {
    }

    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();

    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Command com = null;
		String viewby = request.getParameter("viewby");

		if (viewby == null) {
            com = new Site_ShowAll();
		}
		else if ( viewby.equals("category")) {
            com = new Site_ShowByCategory();
		}
		else if ( viewby.equals("onenews")) {
            com = new Site_ShowOneNews();
		}
        else com = new Site_ShowAll();

        com.execute(request,response);

	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
