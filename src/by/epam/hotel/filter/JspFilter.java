package by.epam.hotel.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.hotel.controller.SessionData;

@WebFilter(urlPatterns = { "/jsp/admin/*", "/jsp/client/*", "/jsp/common/*", "/jsp/error/*" }, 
initParams = { @WebInitParam(name = "INDEX_PATH", value = "/index.jsp") })
public class JspFilter implements Filter {
	private String indexPath;

	public void init(FilterConfig fConfig) throws ServletException {
		indexPath = fConfig.getInitParameter("INDEX_PATH");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		System.out.println("!!!!!!!in filter!!!!!!!!");
		HttpSession session = httpRequest.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		String referer = httpRequest.getHeader("Referer");
		
		if (referer == null) {
			switch (sessionData.getRole()) {
			case CLIENT:
				httpResponse.sendRedirect(httpRequest.getContextPath() +"/controller?command=backtoclientmain");
				break;
			case ADMIN:
				httpResponse.sendRedirect(httpRequest.getContextPath() +"/controller?command=backtoadminmain");
				break;
			case GUEST:
				httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
				break;
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
	}
}
