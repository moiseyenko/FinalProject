package by.epam.hotel.command.impl;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.util.ConfigurationManager;

public class LoginBackCommand implements ActionCommand {

	@Override
	public Router execute(HttpServletRequest request) {
		System.out.println("in LoginBackCommand " + request.getSession().getAttribute("sessionData"));
		Router router = new Router();
		String page = null;
		page = ConfigurationManager.getProperty("path.page.welcome");
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;
	}

}
