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

/**
 * This class is an implementation of a {@link javax.servlet.Filter Filter}
 * interface and is used to prevent jsp's from direct access
 * {@link javax.servlet.ServletRequest request} and
 * {@link javax.servlet.ServletResponse response} to 'UTF-8'.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
@WebFilter(urlPatterns = { "/*" }, initParams = {
		@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param") })
public class EncodingFilter implements Filter {
	private String code;

	/**
	 * The method is called by the web container to indicate to a filter that it is
	 * being placed into service.
	 * 
	 * 
	 * @throws ServletException when servlet encounters difficulty
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		code = fConfig.getInitParameter("encoding");
	}

	/**
	 * The method is used to set the character encoding of request and response to 'UTF-8'.
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
		String codeRequest = request.getCharacterEncoding();

		if (code != null && !code.equalsIgnoreCase(codeRequest)) {
			request.setCharacterEncoding(code);
			response.setCharacterEncoding(code);
		}
		chain.doFilter(request, response);
	}

	/**
	 * The method is called by the web container to indicate to a filter that it is
	 * being taken out of service.
	 */
	public void destroy() {
		code = null;
	}
}