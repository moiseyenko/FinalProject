package by.epam.hotel.command.impl.client;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class BackToRoomsCommand implements ActionCommand {

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		SessionData sessionData = (SessionData) request.getSession().getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT) {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_AVAILABLE_ROOM);
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_LOGIN);
		}
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;
	}
}
