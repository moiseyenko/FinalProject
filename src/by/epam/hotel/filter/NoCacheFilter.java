package by.epam.hotel.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@code NoCacheFilter} is used to remove cache from welcome page.
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
@WebFilter(dispatcherTypes = { DispatcherType.INCLUDE, DispatcherType.REQUEST, DispatcherType.FORWARD }, urlPatterns = {
		"/jsp/common/welcome.jsp" })
public class NoCacheFilter implements Filter {

	/**
	 * The method is called by the web container to indicate to a filter that it is
	 * being placed into service.
	 * 
	 * 
	 * @throws ServletException when servlet encounters difficulty
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

	/**
	 * The method is used to remove cache from welcome page.
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
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");// http 1.1
		httpResponse.setHeader("Pragma", "no-cache");// http 1.0
		httpResponse.setHeader("Expires", "0");// proxie
		chain.doFilter(request, httpResponse);
	}

	/**
	 * The method is called by the web container to indicate to a filter that it is
	 * being taken out of service.
	 */
	public void destroy() {
	}

}
