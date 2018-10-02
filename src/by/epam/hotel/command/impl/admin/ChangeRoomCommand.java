package by.epam.hotel.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.dao.entity.Room;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.OrderLogic;
import by.epam.hotel.util.ConfigurationManager;

public class ChangeRoomCommand implements ActionCommand{
	private static final Logger LOG = LogManager.getLogger(ChangeRoomCommand.class);
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.ADMIN) {
			int roomIndex = Integer.parseInt(request.getParameter("roomIndex"));
			Room roomToChange = sessionData.getRoomList().get(--roomIndex);
			try {
				sessionData.setRoomClasses(OrderLogic.getRoomClassList());
				sessionData.setRoomToChange(roomToChange);
				page = ConfigurationManager.getProperty("path.page.changeroom");
			} catch (ServiceException e) {
				LOG.error(e);
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
