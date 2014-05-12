package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Servlet Filter implementation class FilterAuthAdmin
 */
public class FilterAuthAdmin implements Filter {

	private boolean active = false;
    /**
     * Default constructor. 
     */
    public FilterAuthAdmin() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		
		if(!active) {
			chain.doFilter(request, response);
		}
		else if ( session.getAttribute("userData") != null ) {
			chain.doFilter(request, response);
		}
		else {
//			((HttpServletResponse)response).sendRedirect("AuthController");
			response.getWriter().print("You are not authized!");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		if ( fConfig.getInitParameter("active").toUpperCase().equals("TRUE") ) {
			active = true;
		};
	}

}
