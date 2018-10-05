package by.epam.hotel.command.impl.client;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.OrderLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class OrderCommand implements ActionCommand {
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		SessionData sessionData = (SessionData) request.getSession().getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT) {
			try {
				sessionData.setClients(OrderLogic.getClientList(sessionData.getLogin()));
				sessionData.setNationalities(OrderLogic.getNationalityList());
				sessionData.setRoomClasses(OrderLogic.getRoomClassList());
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ORDER);
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;
	}

}
