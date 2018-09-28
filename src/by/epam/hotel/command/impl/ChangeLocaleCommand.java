package by.epam.hotel.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.LanguageType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;

public class ChangeLocaleCommand implements ActionCommand {

	@Override
	public Router execute(HttpServletRequest request) {
		
		LanguageType locale = LanguageType.valueOf(request.getParameter("locale").toUpperCase());
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		sessionData.setLocale(locale);
		//session.setAttribute("sessionData", sessionData);
		String jspuripath = request.getParameter("jsppath");
		String page = jspuripath.substring(7);
		System.out.println(page);
		Router router = new Router();
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
