package by.epam.hotel.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.AttributeConstant;
import by.epam.hotel.controller.LanguageType;
import by.epam.hotel.controller.ParameterConstant;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;

public class ChangeLocaleCommand implements ActionCommand {

	@Override
	public Router execute(HttpServletRequest request) {
		LanguageType locale = LanguageType.valueOf(request.getParameter(ParameterConstant.LOCALE).toUpperCase());
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		sessionData.setLocale(locale);
		String jspuripath = request.getParameter(ParameterConstant.JSP_PATH);
		String page = jspuripath.substring(7);
		Router router = new Router();
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
