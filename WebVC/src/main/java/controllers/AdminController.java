package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.commands.Command;
import controllers.commands.admin.*;

/**
 * Servlet implementation class AdminController
 */
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String operation = request.getParameter("operation");
		
		Command com = null;
		
		if(operation == null) {
			com = new Admin_Show();
		}
		else if(operation.equals("addnews")) {
			com = new Admin_AddNews();
		} 
		else if(operation.equals("addnewswrite")) {
			com = new Admin_AddNewsWrite();
		}
		else if(operation.equals("editnews")) {
			com = new Admin_EditNews();
		} 
		else if(operation.equals("editnewswrite")) {
			com = new Admin_EditNewsWrite();
		}
		else if(operation.equals("deletenews")) {
			com = new Admin_DeleteNews();
		} 
		else if(operation.equals("deletenewswrite")) {
			com = new Admin_DeleteNewsWrite();
		}
		else com = new Admin_Show();
		
			com.execute(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
