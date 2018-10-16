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

import by.epam.hotel.entity.SessionData;
import by.epam.hotel.util.constant.AttributeConstant;

/**
 * This class is an implementation of a {@link javax.servlet.Filter Filter}
 * interface and is used to prevent jsp from direct access through the browser
 * line.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
@WebFilter(urlPatterns = { "/jsp/*" }, initParams = { @WebInitParam(name = "index_path", value = "/index.jsp"),
		@WebInitParam(name = "client_path", value = "/controller?command=back_to_client_main"),
		@WebInitParam(name = "admin_path", value = "/controller?command=back_to_admin_main") })
public class JspFilter implements Filter {
	private String indexPath;
	private String clientPath;
	private String adminPath;

	/**
	 * The method is called by the web container to indicate to a filter that it is
	 * being placed into service.
	 * 
	 * 
	 * @throws ServletException when servlet encounters difficulty
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		indexPath = fConfig.getInitParameter("index_path");
		clientPath = fConfig.getInitParameter("client_path");
		adminPath = fConfig.getInitParameter("admin_path");

	}

	/**
	 * The method is used to to prevent jsp from direct access through the browser
	 * line by checking request header "Referer". If specified header is null, user
	 * will be redirect to a page depending on his role. If role equals client or
	 * admin, user will be sent to client main and admin main page respectively. If
	 * role equals guest, user will be sent to welcome page.
	 * 
	 * 
	 * @param request  an instance of {@link HttpServletRequest} that provides
	 *                 request information for HTTP servlets.
	 * @param response an instance of {@link HttpServletResponse} that provides
	 *                 HTTP-specificfunctionality in sending a response
	 * @param chain    is used to invoke the next filter in the chain, or if the
	 *                 calling filteris the last filter in the chain, to invoke the
	 *                 resource at the end of the chain
	 * @throws ServletException when servlet encounters difficulty
	 * @throws IOException      when failed or interrupted I/O operations have been
	 *                          occurred
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		String referer = httpRequest.getHeader("Referer");

		if (referer == null) {
			switch (sessionData.getRole()) {
			case CLIENT:
				httpResponse.sendRedirect(httpRequest.getContextPath() + clientPath);
				break;
			case ADMIN:
				httpResponse.sendRedirect(httpRequest.getContextPath() + adminPath);
				break;
			case GUEST:
				httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
				break;
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * The method is called by the web container to indicate to a filter that it is
	 * being taken out of service.
	 */
	public void destroy() {
		indexPath = null;
	}
}
