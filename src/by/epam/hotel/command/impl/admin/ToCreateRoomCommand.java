package by.epam.hotel.command.impl.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.OrderLogic;
import by.epam.hotel.util.ConfigurationManager;

public class ToCreateRoomCommand implements ActionCommand{
	private static final Logger LOG = LogManager.getLogger(ToCreateRoomCommand.class);
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		System.out.println("in ToCreateRoomCommand " + request.getSession().getAttribute("sessionData"));
		Router router = new Router();
		String page = null;
		SessionData sessionData = (SessionData) request.getSession().getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.ADMIN) {
			try {
				sessionData.setRoomClasses(OrderLogic.getRoomClassList());
				page = ConfigurationManager.getProperty("path.page.createroom");
			} catch (ServiceException e) {
				LOG.error(e);
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty("path.page.login");
		}
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;
	}
	
}
