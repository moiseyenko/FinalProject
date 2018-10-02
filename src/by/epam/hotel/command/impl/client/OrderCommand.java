package by.epam.hotel.command.impl.client;

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

public class OrderCommand implements ActionCommand {
	private static final Logger LOG = LogManager.getLogger(OrderCommand.class);
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		SessionData sessionData = (SessionData) request.getSession().getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.CLIENT) {
			try {
				sessionData.setClients(OrderLogic.getClientList(sessionData.getLogin()));
				sessionData.setNationalities(OrderLogic.getNationalityList());
				sessionData.setRoomClasses(OrderLogic.getRoomClassList());
			} catch (ServiceException e) {
				LOG.error(e);
				throw new CommandException(e);
			}
			page = ConfigurationManager.getProperty("path.page.order");
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
		}
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;
	}

}
