package by.epam.hotel.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RefererChangeRequest extends HttpServletRequestWrapper{

	public RefererChangeRequest(HttpServletRequest request) {
		super(request);
	}
	
	
	@Override
	public String getQueryString() {
		String query = null;
		query = ((HttpServletRequest)getRequest()).getQueryString();
		if(query == null) {
			return "Referer=\"\"";
		}
		return query;
	}
	

}
