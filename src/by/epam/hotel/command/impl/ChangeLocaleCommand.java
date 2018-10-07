package by.epam.hotel.command.impl;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.util.apptype.LanguageType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;

public class ChangeLocaleCommand implements ActionCommand {

	@Override
	public Router execute(HttpServletRequest request) {
		LanguageType localeType = LanguageType.valueOf(request.getParameter(ParameterConstant.LOCALE).toUpperCase());
		Locale locale = localeType.getValue();
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
