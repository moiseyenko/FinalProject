package by.epam.hotel.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import by.epam.hotel.controller.SessionData;
import by.epam.hotel.util.type.LanguageType;
import by.epam.hotel.util.type.RoleType;


@WebListener
public class SessionListenerImp implements HttpSessionListener {

	public SessionListenerImp() {

	}

	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		SessionData sessionData = (SessionData)session.getAttribute("sessionData");
		if(sessionData == null) {
			sessionData = new SessionData();
			sessionData.setRole(RoleType.GUEST);
			sessionData.setLocale(LanguageType.RUSSIAN.getValue());
			session.setAttribute("sessionData", sessionData);
		}
		session.setMaxInactiveInterval(60*10);
		System.out.println("sessionCreated: "+ sessionData);
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("sessionDestroyed" + event.getSession().getAttribute("sessionData"));
	}

}
