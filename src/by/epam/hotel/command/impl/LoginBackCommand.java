package by.epam.hotel.command.impl;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.PropertyConstant;

public class LoginBackCommand implements ActionCommand {

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		String page = null;
		page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;
	}

}
