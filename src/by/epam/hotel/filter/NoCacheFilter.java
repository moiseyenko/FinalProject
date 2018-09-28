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
import javax.servlet.http.HttpServletResponse;

@WebFilter (dispatcherTypes = {
		 DispatcherType.INCLUDE,
		 DispatcherType.REQUEST,
		 DispatcherType.FORWARD
		 }, urlPatterns = { "/jsp/welcome.jsp"})
public class NoCacheFilter implements Filter {

    
    public NoCacheFilter() {
       
    }

	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");// http 1.1
		httpResponse.setHeader("Pragma", "no-cache");// http 1.0
		httpResponse.setHeader("Expires", "0");// proxie
		chain.doFilter(request, httpResponse);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
