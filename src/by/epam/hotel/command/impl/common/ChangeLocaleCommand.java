package by.epam.hotel.command.impl.common;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.type.LanguageType;
import by.epam.hotel.util.type.RouterType;

/**
 * This class is an implementation of a {@link by.epam.hotel.command.ActionCommand ActionCommand} interface 
 * and is used to change application language.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ChangeLocaleCommand implements ActionCommand {
	private final String SLASH = "/";
	/**
	 * Method will change application language.
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		LanguageType localeType = LanguageType.valueOf(request.getParameter(ParameterConstant.LOCALE).toUpperCase());
		Locale locale = localeType.getValue();
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		sessionData.setLocale(locale);
		String jspuripath = request.getParameter(ParameterConstant.JSP_PATH);
		System.out.println(jspuripath);
		int rightPathPosition = (jspuripath.indexOf(SLASH, 1));
		String page = jspuripath.substring(rightPathPosition);
		Router router = new Router();
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
