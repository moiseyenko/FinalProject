package by.epam.hotel.command;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class SessionRequestContent {
	HttpServletRequest request;
	private HashMap<String, Object> requestAttributes;
	private HashMap<String, String[]> requestParameters;
	private HashMap<String, Object> sessionAttributes;
	public SessionRequestContent(HttpServletRequest request) {
		this.request = request;

	}
	
	
	
	
	
}
