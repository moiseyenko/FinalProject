package by.epam.hotel.util.type;

/**
 * {@link by.epam.hotel.util.type.RouterType RouterType} is used to provide the
 * way of request transfer
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public enum RouterType {
	// request.getRequestDispatcher(router.getPage()).forward(request, response);
	/**
	 * This type represents
	 * {@link javax.servlet.RequestDispatcher#forward(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 * RequestDispatcher.forward} that happens entirely on the server. The servlet
	 * container forwards the same request to the target url with the same request
	 * attributes and the same request parameters when handling the new url
	 */
	FORWARD,
	/**
	 * This type represents
	 * {@link javax.servlet.http.HttpServletResponse#sendRedirect(String)
	 * responce.sendRedirect} that sets the new url in a Location
	 * header, and sends the response to the browser. Then the browser, according to
	 * the http specification, makes another request to the new url.
	 */
	REDIRECT
}