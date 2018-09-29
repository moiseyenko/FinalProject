package by.epam.hotel.filter;

import java.io.IOException;
import java.util.Enumeration;

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

import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.SessionData;

@WebFilter(urlPatterns = { "/jsp/*" }, initParams = { @WebInitParam(name = "INDEX_PATH", value = "/index.jsp") })
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
		
		if(sessionData != null&&(sessionData.getRole()==RoleType.CLIENT||sessionData.getRole()==RoleType.ADMIN)) {
			System.out.println("dofilter");
			chain.doFilter(request, response);
		}
		/*if (  sessionData.isInnerRedirect()) {
			System.out.println("dofilter");
			//sessionData.setInnerRedirect(false);
			chain.doFilter(request, response);
		}*/ else {
			System.out.println("redirect");
			httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
		}
	}

	public void destroy() {
	}
}
