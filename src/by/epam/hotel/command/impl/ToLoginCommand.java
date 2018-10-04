package by.epam.hotel.command.impl;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.PropertyConstant;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.util.ConfigurationManager;

public class ToLoginCommand implements ActionCommand {

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		String page = ConfigurationManager.getProperty(PropertyConstant.PAGE_LOGIN);
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;

	}

}
