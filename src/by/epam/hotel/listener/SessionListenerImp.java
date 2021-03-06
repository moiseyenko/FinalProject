package by.epam.hotel.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import by.epam.hotel.entity.SessionData;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.type.LanguageType;
import by.epam.hotel.util.type.RoleType;

/**
 * The class is an implementation of HttpSessionListener and is used to create
 * new session with an attribute 'sessionData' which includes fields role with
 * value 'GUEST' and locale with value 'RUSSIAN'.
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
@WebListener
public class SessionListenerImp implements HttpSessionListener {

	/**
	 * The method is used to create new session with an attribute 'sessionData' which includes fields role
	 * with value 'GUEST' and locale with value 'RUSSIAN'.
	 * 
	 * 
	 * @param event the HttpSessionEvent containing the session
	 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData == null) {
			sessionData = new SessionData();
			sessionData.setRole(RoleType.GUEST);
			sessionData.setLocale(LanguageType.RUSSIAN.getValue());
			session.setAttribute("sessionData", sessionData);
		}
		session.setMaxInactiveInterval(60 * 10);
	}

	public void sessionDestroyed(HttpSessionEvent event) {
	}

}
