package by.epam.hotel.command.impl;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.AttributeConstant;
import by.epam.hotel.controller.PropertyConstant;
import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;

public class ToChangePersonalDataCommand implements ActionCommand{

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		SessionData sessionData = (SessionData) request.getSession().getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT||sessionData.getRole() == RoleType.ADMIN) {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_PERSONAL_DATA);
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_LOGIN);
		}
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;
	}

}
