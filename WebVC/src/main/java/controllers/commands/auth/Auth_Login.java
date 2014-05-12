package controllers.commands.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.jdbc.NewsFeedDAO;
import model.dao.jdbc.data.User;

public class Auth_Login {
       

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String afterLoginRedirect = "admin/AdminController";
		
		NewsFeedDAO dao = NewsFeedDAO.getNewsFeedDAO();
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher;
		
		User user = dao.getUser(request.getParameter("email"));
		if (user != null && user.getPass().equals(request.getParameter("pass"))) {
			
			if (request.getAttribute("loginFailedMsg") != null) {
				request.removeAttribute("loginFailedMsg");
			}
			
			session.setAttribute("userData", user);
			request.setAttribute("authorizated", "true");
			try {
				response.sendRedirect(afterLoginRedirect);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			request.setAttribute("loginFailedMsg", "Error. May be, Login or Password incorrect. Try again, please.");
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authPage.jsp");
			try {
				dispatcher.forward(request, response);
			} 
			catch (ServletException e) {System.out.print("Auth_Login: dispatcher.forward failed"); e.printStackTrace(); }
			catch (IOException e) { System.out.print("Auth_Login: dispatcher.forward failed"); e.printStackTrace(); }
		}
		
		
		
	}

}
