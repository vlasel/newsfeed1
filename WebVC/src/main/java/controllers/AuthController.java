package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.commands.auth.Auth_Login;
import controllers.commands.auth.Auth_Logout;

/**
 * Servlet implementation class AuthController
 */
//@WebServlet("/AuthController")
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("operation") == null) {
			/*RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authPage.jsp");
			dispatcher.forward(request, response);*/
			try {
                request.getRequestDispatcher("/WEB-INF/jsp/authPage.jsp").forward(request,response);
//				response.sendRedirect("/WEB-INF/jsp/authPage.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if ( request.getParameter("operation").equals("login") ) {
			request.removeAttribute("operation");
			Auth_Login authLogin = new Auth_Login();
			authLogin.execute(request, response);
		}
		else if ( request.getParameter("operation").equals("logout") ) {
			request.removeAttribute("operation");
			Auth_Logout authLogout = new Auth_Logout();
			authLogout.execute(request, response);
		}
		
		

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}

}
