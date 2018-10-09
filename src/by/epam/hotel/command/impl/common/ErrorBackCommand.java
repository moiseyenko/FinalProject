package by.epam.hotel.command.impl.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RouterType;

public class ErrorBackCommand implements ActionCommand{

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		System.out.println("ROLE:"+ sessionData.getRole());
		switch (sessionData.getRole()) {
		case GUEST: 
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_LOGIN);
			break;
		case CLIENT: 
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CLIENTMAIN);
			break;
		case ADMIN:
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ADMIN_MAIN);
			break;
		}
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;
	}

}
