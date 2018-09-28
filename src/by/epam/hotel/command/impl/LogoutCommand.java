package by.epam.hotel.command.impl;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.LanguageType;
import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.util.ConfigurationManager;

public class LogoutCommand implements ActionCommand {

	@Override
	public Router execute(HttpServletRequest request) {
		System.out.println("in LogoutCommand");
		request.getSession().invalidate();
		SessionData sessionData = new SessionData();
		sessionData.setRole(RoleType.GUEST);
		sessionData.setLocale(LanguageType.RUSSIAN);
		sessionData.setInnerRedirect(true);
		request.getSession().setAttribute("sessionData", sessionData);
		Router router = new Router();
		String page = ConfigurationManager.getProperty("path.page.welcome");
		router.setPage(page);
		router.setType(RouterType.REDIRECT);
		return router;
	}

}
